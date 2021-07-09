package uz.pdp.lesson11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson11.entity.Attachment;


public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}
