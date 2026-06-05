package com.pao.project.service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public class ServiceAudit {

    private static com.pao.project.service.ServiceAudit INSTANCE;
    private final ReentrantLock blocare = new ReentrantLock();
    private final String fisierAudit = "audit.csv";

    private ServiceAudit() {
    }

    public static com.pao.project.service.ServiceAudit getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new com.pao.project.service.ServiceAudit();
        }
        return INSTANCE;
    }

    public void scrieActiune(String numeActiune) {
        blocare.lock();


        try (FileWriter scriitor = new FileWriter(fisierAudit, true)) {
            scriitor.write(numeActiune + "," + LocalDateTime.now() + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Eroare la scrierea auditului", e);
        } 
        finally {
            blocare.unlock();
        }
    }
}
