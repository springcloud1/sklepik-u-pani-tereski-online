package pl.pani.tereska.online.payments.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.pani.tereska.online.payments.model.Payment;
import pl.pani.tereska.online.payments.model.ResultPage;
import pl.pani.tereska.online.payments.repository.PaymentsRepository;

@RequiredArgsConstructor
@Service
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
