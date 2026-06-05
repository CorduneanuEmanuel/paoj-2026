package com.pao.project;

import static java.util.Collections.sort;
import java.util.List;
import java.util.Scanner;

import com.pao.project.model.Client;
import com.pao.project.model.Comanda;
import com.pao.project.model.ComparatorVanzariRestaurant;
import com.pao.project.model.Firma;
import com.pao.project.model.Livrator;
import com.pao.project.model.Locatie;
import com.pao.project.model.Pozitie;
import com.pao.project.model.Produs;
import com.pao.project.model.Restaurant;
import com.pao.project.service.ServiceAudit;
import com.pao.project.service.ServiceClient;
import com.pao.project.service.ServiceComanda;
import com.pao.project.service.ServiceFirma;
import com.pao.project.service.ServiceLivrator;
import com.pao.project.service.ServiceRestaurant;
import com.pao.project.util.InitializareBazaDate;

public class Main {
    public static void main(String[] args) {

        InitializareBazaDate.getInstance().initializare();

        Scanner scanner = new Scanner(System.in);

        ServiceFirma serviceFirma = ServiceFirma.getInstance();
        ServiceRestaurant serviceRestaurant = ServiceRestaurant.getInstance();
        ServiceClient serviceClient = ServiceClient.getInstance();
        ServiceLivrator serviceLivrator = ServiceLivrator.getInstance();
        ServiceComanda serviceComanda = ServiceComanda.getInstance();
        ServiceAudit auditService = ServiceAudit.getInstance();

        int optiune = 0;

        do {
            System.out.println("Alege optiunea:\n");
            System.out.println("0. Exit");
            System.out.println("1. Adauga firma");
            System.out.println("2. Adauga client");
            System.out.println("3. Adauga restaurant");
            System.out.println("4. Adauga sofer");
            System.out.println("5. Plaseaza comanda");
            System.out.println("6. Asigneaza sofer comanda");
            System.out.println("7. Finalizeaza livrare");
            System.out.println("8. Listeaza comenzi client");
            System.out.println("9. Top restaurante");
            System.out.println("10. Adaugare meniu restaurant");
            System.out.println("11. Raport comenzi pe clienti");
            System.out.println("12. Raport top produse");
            System.out.println("13. Raport comenzi active");

            optiune = scanner.nextInt();
            switch (optiune) {
                case 1: {
                    System.out.println("CUI:");
                    int cui = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Nume firma:");
                    String nume = scanner.next();

                    System.out.println("Numar ordine registru comertului:");
                    String nr_ordine = scanner.next();

                    serviceFirma.adaugaFirma(new Firma(cui, nr_ordine, nume));
                    auditService.scrieActiune("adauga_firma");
                    System.out.println("Firma adaugata");
                    break;
                }

                case 2: {
                    System.out.println("ID client:");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Nume client:");
                    String nume = scanner.nextLine();

                    serviceClient.adaugaClient(new Client(id, nume));
                    auditService.scrieActiune("adauga_client");
                    System.out.println("Client adaugat");
                    break;
                }

                case 3: {
                    System.out.println("CUI firma:");
                    int cui = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("ID restaurant:");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Nume restaurant:");
                    String nume = scanner.nextLine();

                    System.out.println("Adresa:");
                    String adresa = scanner.nextLine();

                    System.out.println("Oras:");
                    String oras = scanner.nextLine();

                    System.out.println("Pozitie x-restaurant:");
                    double x = scanner.nextDouble();

                    System.out.println("Pozitie y-restaurant:");
                    double y = scanner.nextDouble();

                    Restaurant r = new Restaurant(cui, id, nume, new Locatie(adresa, oras, new Pozitie(x, y)));
                    serviceFirma.adaugaRestaurantFirma(cui, r);
                    serviceRestaurant.adaugaRestaurant(r);
                    auditService.scrieActiune("adauga_restaurant");

                    System.out.println("Restaurant adaugat");
                    break;
                }

                case 4: {
                    System.out.println("ID sofer:");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Nume sofer:");
                    String nume = scanner.nextLine();

                    System.out.println("X:");
                    double x = scanner.nextDouble();

                    System.out.println("Y:");
                    double y = scanner.nextDouble();

                    Livrator sofer = new Livrator(id, nume, new Pozitie(x, y));
                    serviceLivrator.adaugaSofer(sofer);
                    auditService.scrieActiune("adauga_sofer");

                    System.out.println("Sofer adaugat cu succes");

                    break;

                }

                case 5: {

                    System.out.print("ID comanda: ");
                    String id = scanner.next();

                    System.out.print("ID client: ");
                    int clientId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Locatie client:");
                    System.out.print("Oras livrare:");
                    String oras = scanner.next();
                    System.out.print("Adresa livrare:");
                    String adresaLivrare = scanner.next();
                    System.out.print("X:");
                    double xLivrare = scanner.nextDouble();
                    System.out.print("Y:");
                    double yLivrare = scanner.nextDouble();
                    Pozitie pozitieLivrare = new Pozitie(xLivrare, yLivrare);
                    Locatie locatieClient = new Locatie(oras, adresaLivrare, pozitieLivrare);


                    System.out.print("CUI firma: ");
                    int cui = scanner.nextInt();

                    System.out.print("ID restaurant: ");
                    int restId = scanner.nextInt();

                    Client client = serviceClient.cautaClient(clientId);
                    if (client == null) {
                        System.out.println("Clientul nu exista");
                        break;
                    }

                    Firma firma = serviceFirma.cautaFirma(cui);
                    if (firma == null) {
                        System.out.println("Firma nu exista");
                        break;
                    }

                    Restaurant restaurant = null;

                    for (Restaurant r : firma.getRestaurante()) {
                        if (r.getId() == restId) {
                            restaurant = r;
                            break;
                        }
                    }

                    if (restaurant == null) {
                        System.out.println("Restaurantul nu exista");
                        break;
                    }

                    Comanda comanda = new Comanda(id, client, restaurant, locatieClient);
                    if (restaurant.getMeniu().isEmpty()) {
                        System.out.println("Restaurantul nu are produse in meniu");
                        break;
                    }

                    System.out.println("Produse disponibile:");
                    for (Produs p : restaurant.getMeniu()) {
                        System.out.println(p.getId() + " - " + p.getNume());
                    }

                    System.out.println("Cate produse vrei sa adaugi?");
                    int nr = scanner.nextInt();

                    for (int i = 0; i < nr; i++) {
                        System.out.print("ID produs: ");
                        int prodId = scanner.nextInt();
                        boolean gasit = false;

                        for (var p : restaurant.getMeniu()) {
                            if (p.getId() == prodId) {
                                comanda.adaugaProdus(p);
                                gasit = true;
                                break;
                            }
                        }
                        if (!gasit) {
                            System.out.println("Produs invalid");
                        }
                    }

                    serviceComanda.adaugaComanda(comanda);
                    auditService.scrieActiune("plaseaza_comanda");
                    System.out.println("Comanda plasata cu succes");
                    break;
                }

                case 6: {

                    System.out.print("ID comanda: ");
                    String id = scanner.next();

                    Comanda comanda = serviceComanda.cautaComanda(id);
                    if (comanda == null) {
                        System.out.println("Comanda nu exista");
                        break;
                    }

                    Restaurant restaurant = comanda.getRestaurant();
                    if (restaurant == null) {
                        System.out.println("Restaurant invalid");
                        break;
                    }

                    try {
                        Livrator sofer = serviceLivrator.celMaiApropiat(restaurant.getLocatie());
                        comanda.setSofer(sofer);
                        sofer.setDisponibil(false);
                        serviceComanda.updateComanda(comanda);
                        serviceLivrator.updateSofer(sofer);
                        auditService.scrieActiune("asigneaza_sofer_comanda");
                        System.out.println("Sofer asignat cu succes");
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }

                case 7: {

                    System.out.print("ID comanda: ");
                    String id = scanner.next();

                    serviceComanda.finalizeazaLivrare(id);
                    auditService.scrieActiune("finalizeaza_livrare");
                    System.out.println("Comanda finalizata");
                    break;
                }

                case 8: {
                    System.out.print("ID client: ");
                    int clientId = scanner.nextInt();
                    boolean gasit = false;

                    for (Comanda comanda : serviceComanda.getComenzi()) {
                        if (comanda.getClient().getId() == clientId) {
                            System.out.println(comanda.getId());
                            gasit = true;
                        }
                    }
                    if (!gasit) {
                        System.out.println("Nu exista comenzi pentru acest client");
                    }
                    auditService.scrieActiune("listeaza_comenzi_client");
                    break;
                }

                case 9: {
                    List<Restaurant> restaurante = serviceFirma.getToateRestaurantele();

                    if (restaurante.isEmpty()) {
                        System.out.println("Nu exista restaurante");
                        break;
                    }
                    sort(restaurante, new ComparatorVanzariRestaurant());
                    for (Restaurant r : restaurante) {
                        System.out.println(r.getNume() + " " + r.getNrVanzari() + " comenzi");
                    }
                    auditService.scrieActiune("top_restaurante");

                    break;
                }

                case 10: {
                    System.out.println("CUI firma:");
                    int cui = scanner.nextInt();

                    System.out.println("Id restaurant:");
                    int idRestaurant = scanner.nextInt();

                    Firma firma = serviceFirma.cautaFirma(cui);

                    if (firma == null) {
                        throw new RuntimeException("Firma nu exista");

                    }
                    Restaurant restaurant = null;
                    for (Restaurant r : firma.getRestaurante()) {
                        if (r.getId() == idRestaurant) {
                            restaurant = r;
                            break;
                        }
                    }

                    if (restaurant == null) {
                        throw new RuntimeException("Restaurantul nu exista");
                    }

                    restaurant.setMeniu();
                    serviceRestaurant.updateRestaurant(restaurant);
                    auditService.scrieActiune("adauga_meniu_restaurant");
                    System.out.println("Meniu modificat cu succes.");
                    break;
                }

                case 11: {
                    List<String> raport = serviceComanda.raportComenziPeClient();
                    for (String linie : raport) {
                        System.out.println(linie);
                    }
                    auditService.scrieActiune("raport_comenzi_clienti");
                    break;
                }

                case 12: {
                    List<String> raport = serviceComanda.raportTopProduse();
                    for (String linie : raport) {
                        System.out.println(linie);
                    }
                    auditService.scrieActiune("raport_top_produse");
                    break;
                }

                case 13: {
                    List<String> raport = serviceComanda.raportComenziActive();
                    for (String linie : raport) {
                        System.out.println(linie);
                    }
                    auditService.scrieActiune("raport_comenzi_active");
                    break;
                }

                case 0:
                    System.out.println("Exit");
                    break;

                default:
                    System.out.println("Optiune invalida");
            }
        } while (optiune != 0);

    }
}
