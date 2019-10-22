package pl.pani.tereska.online.payments.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pani.tereska.online.payments.dto.PageDto;
import pl.pani.tereska.online.payments.dto.PaymentDto;
import pl.pani.tereska.online.payments.model.Mapper;
import pl.pani.tereska.online.payments.model.Payment;
import pl.pani.tereska.online.payments.model.ResultPage;
import pl.pani.tereska.online.payments.services.PaymentsService;

import java.net.URI;
import java.util.List;

@RequestMapping(value = "payments")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    @NonNull
    private PaymentsService paymentsService;
    @NonNull
    private Mapper mapper;

    private UriBuilder uriBuilder = new UriBuilder();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity payment(@RequestBody PaymentDto paymentDto) {
        Payment payment = mapper.map(paymentDto, Payment.class);
        paymentsService.payment(payment);
        URI uri = uriBuilder.requestUriWithId(payment.getId());
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public PageDto<PaymentDto> getUsers(
            @RequestParam(required = false, defaultValue = "0", name = "pageNumber") int pageNumber,
            @RequestParam(required = false, defaultValue = "10", name = "pageSize") int pageSize) {
        ResultPage<Payment> resultPage = paymentsService.getPayments(pageNumber, pageSize);
        List<PaymentDto> usersDtos = mapper.map(resultPage.getContent(), PaymentDto.class);
        return new PageDto<>(usersDtos, resultPage.getPageNumber(), resultPage.getTotalPages());
    }
}
