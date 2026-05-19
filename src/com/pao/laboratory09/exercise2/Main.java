package com.pao.laboratory09.exercise2;
import com.pao.laboratory09.exercise1.TipTranzactie;

import com.pao.laboratory09.exercise1.TipTranzactie;
import com.pao.laboratory09.exercise1.Tranzactii;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip)
        // 2. Scrie toate înregistrările în OUTPUT_FILE cu DataOutputStream (format binar, RECORD_SIZE=32 bytes/înreg.)
        //    - bytes 0-3:   id (int, little-endian via ByteBuffer)
        //    - bytes 4-11:  suma (double, little-endian via ByteBuffer)
        //    - bytes 12-21: data (String, 10 chars ASCII, paddat cu spații la dreapta)
        //    - byte 22:     tip (0=CREDIT, 1=DEBIT)
        //    - byte 23:     status (0=PENDING, 1=PROCESSED, 2=REJECTED)
        //    - bytes 24-31: padding (zerouri)
        // 3. Procesează comenzile din stdin până la EOF cu RandomAccessFile:
        //    - READ idx       → seek(idx * RECORD_SIZE), citește și afișează înregistrarea
        //    - UPDATE idx ST  → seek(idx * RECORD_SIZE + 23), scrie noul status (0/1/2)
        //                       afișează "Updated [idx]: STATUS"
        //    - PRINT_ALL      → citește și afișează toate înregistrările
        //
        // Format linie output:
        //   [idx] id=<id> data=<data> tip=<CREDIT|DEBIT> suma=<suma:.2f> RON status=<STATUS>

        String FILE = "output/lab09_ex2.ser";
        File file = new File(FILE);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        ArrayList<Tranzactii> tranzactii = new ArrayList<>();
        try(DataOutputStream d = new DataOutputStream(new FileOutputStream(file)))
        {
            for(int i = 0; i < n; i++){
                int id = scanner.nextInt();

                double suma = scanner.nextDouble();

                String data = scanner.next();

//            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

//            Tranzactii tr = new Tranzactii(id, suma, data, tip.toString())

                String tip = scanner.next();

//                String status = scanner.next();

                d.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(id).array());
                d.write(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(suma).array());

                while(data.length()<10){
                    data = data.concat(" ");
                }
                d.write(data.getBytes());

                d.write(tip.equals("CREDIT") ? (byte) 0 : (byte) 1);

                d.write((byte) 0);
//                if(status.equals("PENDING")){
//                    d.write((byte) 0);
//                }
//                else if(status.equals("PROCESSED")){
//                    d.write((byte) 1);
//                }
//                else{
//                    d.write((byte) 2);
//                }

                byte[] bytes = {0, 0, 0, 0, 0, 0, 0, 0};
                d.write(bytes);

            }

        }
        catch(IOException e){

        }

        try(RandomAccessFile raf = new RandomAccessFile(FILE, "rw")) {

            String format = "[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s";

            while (scanner.hasNext()) {
                String comanda = scanner.next();

                if (comanda.equals("READ")) {
                    int idx = scanner.nextInt();

                    raf.seek(idx * 32L);

                    byte[] record = new byte[32];
                    raf.read(record);

                    ByteBuffer buff = ByteBuffer.wrap(record).order(ByteOrder.LITTLE_ENDIAN);

                    int id = buff.getInt(0);

                    double suma = buff.getDouble(4);

                    String data = new String(record, 4 + 8,  10, StandardCharsets.UTF_8);

                    String tip = record[22] == (byte) 0 ? "CREDIT" : "DEBIT";

                    String status = "";
                    byte st = record[23];
                    if(st == (byte) 0){
                        status = "PENDING";
                    }
                    else if(st == (byte) 1){
                        status = "PROCESSED";
                    }
                    else{
                        status = "REJECTED";
                    }

                    System.out.println(String.format(format, idx, id, data, tip, suma, status));

                }
                else if (comanda.equals("UPDATE")) {

                    int idx = scanner.nextInt();

                    String status = scanner.next();
                    byte byte_status = 0;
                    if(status.equals("PENDING")){
                        byte_status = 0;
                    }
                    else if(status.equals("PROCESSED")){
                        byte_status = 1;
                    }
                    else if(status.equals("REJECTED")){
                        byte_status = 2;
                    }

                    raf.seek(idx * 32L + 23);
                    raf.write(byte_status);

                    System.out.println(String.format("Updated [%d]: %s", idx, status));
                }
                else if (comanda.equals("PRINT_ALL")) {
                    for(int idx = 0; idx < n; idx++) {
                        raf.seek(idx * 32L);

                        byte[] record = new byte[32];
                        raf.read(record);

                        ByteBuffer buff = ByteBuffer.wrap(record).order(ByteOrder.LITTLE_ENDIAN);

                        int id = buff.getInt(0);

                        double suma = buff.getDouble(4);

                        String data = new String(record, 4 + 8,  10, StandardCharsets.UTF_8);

                        String tip = record[22] == (byte) 0 ? "CREDIT" : "DEBIT";

                        String status = "";
                        byte st = record[23];
                        if (st == (byte) 0) {
                            status = "PENDING";
                        } else if (st == (byte) 1) {
                            status = "PROCESSED";
                        } else {
                            status = "REJECTED";
                        }

                        System.out.println(String.format(format, idx, id, data, tip, suma, status));
                    }
                }


            }
        }
        catch(IOException e){



        }







//        Byte[] = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt().array()





    }
}
