package com.code.booksystem.Repository;
import com.code.booksystem.Entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {
    Optional<Library> findByIsbn(Long isbn); // Add this method
    boolean existsByIsbn(Long isbn); // Ensure you have this for ISBN check
}
