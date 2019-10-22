package pl.pani.tereska.online.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pani.tereska.online.payments.model.Payment;

public interface PaymentsRepository extends JpaRepository<Payment, Long> {
}
