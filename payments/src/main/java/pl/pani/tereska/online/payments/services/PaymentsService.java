package pl.pani.tereska.online.payments.services;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.pani.tereska.online.payments.model.Payment;
import pl.pani.tereska.online.payments.model.ResultPage;
import pl.pani.tereska.online.payments.repository.PaymentsRepository;

public class PaymentsService {

    @NonNull
    private PaymentsRepository paymentsRepository;

    public void payment(Payment payment) {
        paymentsRepository.saveAndFlush(payment);
        // next line should send payment to external source.
    }

    public ResultPage<Payment> getPayments(int pageNumber, int pageSize) {
        Page<Payment> usersPage = paymentsRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return new ResultPage<>(usersPage.getContent(), usersPage.getNumber(), usersPage.getTotalPages());
    }
}
