package id.ac.ub.filkom.perpustakaan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Titik masuk aplikasi. @SpringBootApplication mengaktifkan konfigurasi otomatis
 * dan memindai (component scan) seluruh kelas di package ini dan sub-package-nya.
 */
@SpringBootApplication
public class PerpustakaanApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerpustakaanApplication.class, args);
    }
}
