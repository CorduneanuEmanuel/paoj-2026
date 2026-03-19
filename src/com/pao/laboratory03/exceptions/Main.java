package com.pao.laboratory03.exceptions;

import java.util.*;

/**
 * Exercițiul 3 — Excepții (checked, unchecked, custom)
 *
 * Creează în acest pachet (lângă Main.java) două clase de excepții custom,
 * apoi demonstrează-le aici.
 *
 * PASUL 1 — Creează InvalidAgeException.java (fișier separat):
 *   - Extinde RuntimeException (unchecked)
 *   - Constructor cu String message → apelează super(message)
 *
 * PASUL 2 — Creează DuplicateEntryException.java (fișier separat):
 *   - Extinde RuntimeException (unchecked)
 *   - Constructor cu String message → apelează super(message)
 *
 * PASUL 3 — În acest Main.java, implementează și demonstrează:
 *
 *   a) UNCHECKED EXCEPTIONS — NullPointerException, ArrayIndexOutOfBoundsException:
 *      - Creează o metodă riskyMethod() care aruncă NullPointerException
 *      - Prinde-o cu try-catch, afișează mesajul erorii
 *      - Adaugă un bloc finally care se execută mereu
 *
 *   b) CUSTOM EXCEPTIONS — InvalidAgeException, DuplicateEntryException:
 *      - Creează o metodă validateAge(int age) care aruncă InvalidAgeException
 *        dacă age < 0 sau age > 150
 *      - Creează o metodă addToList(List<String> list, String name) care aruncă
 *        DuplicateEntryException dacă name există deja în listă
 *      - Demonstrează ambele cu try-catch
 *
 *   c) MULTI-CATCH:
 *      - Prinde InvalidAgeException | DuplicateEntryException într-un singur catch
 *
 *   d) CATCH ORDERING:
 *      - Demonstrează că prinderea specifică (InvalidAgeException) trebuie
 *        să fie ÎNAINTE de cea generală (RuntimeException)
 *
 *   e) THROW vs THROWS:
 *      - Creează o metodă cu semnătura: void process(int age) throws InvalidAgeException
 *      - Apeleaz-o din main cu try-catch
 *
 * Output așteptat:
 *
 * === a) Unchecked — NullPointerException ===
 * Prins: Cannot invoke "String.length()" because "s" is null
 * Finally se execută mereu!
 *
 * === b) Custom exceptions ===
 * InvalidAgeException: Vârsta -5 nu este validă (0-150)
 * DuplicateEntryException: 'Ana' există deja în listă
 *
 * === c) Multi-catch ===
 * Excepție prinsă: Vârsta 200 nu este validă (0-150)
 *
 * === d) Catch ordering (specific → general) ===
 * InvalidAgeException prinsă specific: Vârsta -1 nu este validă (0-150)
 *
 * === e) Throw vs throws ===
 * Metoda process() a aruncat: Vârsta 999 nu este validă (0-150)
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // TODO: implementează pașii de mai sus
        // Hint: creează mai întâi InvalidAgeException.java și DuplicateEntryException.java
        try{
            riskymethod();
        }
        catch(NullPointerException e){

            System.out.println(e.getMessage());

        } finally{
            System.out.println("Am verifiact mereu eroarea");
        }

        try{
            int age= 151;
            validateage(age);


        }catch(InvalidAgeException e){
            System.out.println(e.getMessage());

        }

        try{
            String nume= scanner.next();
            ArrayList<String> lista = new ArrayList<>();
            lista.add("Emi");
            lista.add("Andrei");
            addToList(lista, nume);
        }
        catch(DuplicateEntryException e1){
            System.out.println(e1.getMessage());

        }


//d
        try {
            validateage(151);
        } catch (InvalidAgeException | DuplicateEntryException e) {

            System.out.println("Specific: " + e.getMessage());
        } catch (RuntimeException e) {

            System.out.println("General: " + e.getMessage());
        }
        try {
            validateage(151);
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }


//        throw vs throws
        try {
            validateage(-1);
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void riskymethod(){
//

        String nume=null;
//        nume.toUpperCase();
        nume.length();
        throw new NullPointerException("Este o aruncare a unui erori");

    }

    public static void validateage(int age){
        if (age < 0 || age > 150){
            throw new InvalidAgeException("Varsta e nerecorespunzatoare");

        }
    }

    public static void addToList(List<String> list, String name) {
        if (list.contains(name)) {
            throw new DuplicateEntryException("Da , sunt duplicate");
        } else {
            list.add(name);
        }
    }

    public static void process(int age) throws InvalidAgeException{
        validateage(age);

    }




}

