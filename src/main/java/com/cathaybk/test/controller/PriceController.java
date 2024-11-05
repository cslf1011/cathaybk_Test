package com.cathaybk.test.controller;


import com.cathaybk.test.domain.Price;
import com.cathaybk.test.service.PriceChangeCalculatorService;
import com.cathaybk.test.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

@RestController
@RequestMapping("/price")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @Autowired
    private PriceChangeCalculatorService priceChangeCalculatorService;

    // 查詢某日價格的，寫一個 API,查詢某日價格。
    @GetMapping("/date")
    public ResponseEntity<?> getPriceByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//    public ResponseEntity<BigDecimal> getPricesByDate(@RequestParam("date") String date) {
//        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(date);

        boolean existsed = priceService.existsByDate(date);
        if (!existsed) {
            return ResponseEntity.notFound().build();
        }

        if (date != null) {
            Price price = priceService.findByDate(date);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(price.getPrice());
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    //新增，寫一個 API,新增價格至 DB。
    @PostMapping("/add")
    public ResponseEntity<String> addPrice(@RequestBody Price price) {
        boolean existsed = priceService.existsByDate(price.getDate());
        if (existsed) {
            return ResponseEntity.badRequest().body("該日期的價格已存在，無法重複新增.");
        }

        priceService.addPrice("10480016", price.getDate(), price.getPrice());
        return ResponseEntity.ok("價格新增成功");

    }


    //依日期修改價格，寫一個 API,修改某日價格。
    @PutMapping("/{date}")
    public ResponseEntity<String> updatePrice(
            @PathVariable String date,
            @RequestBody Map<String, BigDecimal> requestBody) {

        System.out.println(date);
        System.out.println(requestBody.get("newPrice"));

        // 檢查日期是否為空
        if (date == null || requestBody.isEmpty()) {
            return ResponseEntity.badRequest().body("請確認輸入資料是否正確");
        }

        LocalDate localDate;
        // 轉LocalDate
        try {
            localDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("請輸入格式 YYYY-MM-DD.");
        }


        BigDecimal newPrice = requestBody.get("newPrice");
        if (newPrice == null) {
            return ResponseEntity.badRequest().body("新價格是必需的.");
        }

        //更新
        int result = priceService.updatePriceByDate(localDate, newPrice);

        //結果
        if (result >= 1) {
            return ResponseEntity.ok("價格更新成功.");
        } else {
            return ResponseEntity.status(404).body("更新失敗，未找到該日期的價格.");
        }
    }

    //刪除，寫一個 API,可刪除某日價格。
    @DeleteMapping("/{date}")
    public ResponseEntity<String> deletePrice(@PathVariable String date) {
        LocalDate localDate;
        if (date == null) {
            return ResponseEntity.badRequest().body("請確認輸入資料是否正確");
        }
        try {
            localDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("請輸入格式 YYYY-MM-DD.");
        }
        boolean existsed = priceService.existsByDate(localDate);
        if (!existsed) {
            return ResponseEntity.badRequest().body("該日期不存在，請重新查訊日期價格.");
        }
        int r = priceService.deleteByDate(localDate);
        if (r == 1) {
            return ResponseEntity.ok("價格刪除成功.");
        } else {
            return ResponseEntity.ok("價格刪除失敗.");

        }


    }


    //計算漲跌，寫 interface 去帶入開始結束時間,分別實作計算漲跌[後收-前收]和漲跌
    //幅[(後收-前收)/前收]
    @GetMapping("/change")
    public ResponseEntity<String> getPriceChange(@RequestParam String startDate, @RequestParam String endDate) {
        // 檢查日期格式
        LocalDate start;
        LocalDate end;
        try {
            start = LocalDate.parse(startDate);
            end = LocalDate.parse(endDate);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("日期格式錯誤，請使用 YYYY-MM-DD 格式");
        }
        // 檢查日期順序
        if (start.isAfter(end)) {
            return ResponseEntity.badRequest().body("結束日期必須在開始日期之後");
        }
        System.out.println(start);
        System.out.println(end);
        String r = priceChangeCalculatorService.calculatePriceChange(start, end);

        System.out.println(r);

        return ResponseEntity.ok(r);
    }


    //算漲幅
    @GetMapping("/change/percentage")
    public ResponseEntity<String> getChangePercentage(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start;
        LocalDate end;
        try {
            start = LocalDate.parse(startDate);
            end = LocalDate.parse(endDate);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("日期格式錯誤，請使用 YYYY-MM-DD 格式");
        }
        // 檢查日期順序
        if (start.isAfter(end)) {
            return ResponseEntity.badRequest().body("結束日期必須在開始日期之後");
        }
        String p=priceChangeCalculatorService.calculatePriceChangePercentage(start,end);

        System.out.println(p);
        return ResponseEntity.ok(p);
    }
}
