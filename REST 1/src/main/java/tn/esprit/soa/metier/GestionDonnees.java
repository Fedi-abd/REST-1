package tn.esprit.soa.metier;

import tn.esprit.soa.entities.Etudiant;
import tn.esprit.soa.entities.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GestionDonnees {
    public static List<Option> options = new ArrayList<>();
    public static List<Etudiant> etudiants = new ArrayList<>();

    static {
        // Initial data matching some examples from the PDF
        Option o1 = new Option(1, "Informatique", "Informatique", "M. Responsable Informatique", 30, 1, 30);
        Option o2 = new Option(2, "Mathématiques", "Mathématiques", "Mme Responsable Mathématiques", 25, 1, 25);
        options.add(o1);
        options.add(o2);

        etudiants.add(new Etudiant("I001", "Doe", "Jean", o1, 2023, "jean.doe@example.com"));
        etudiants.add(new Etudiant("I002", "Smith", "Alice", o1, 2022, "alice.smith@example.com"));
    }

    // --- Option methods ---

    public static boolean addOption(Option o) {
        if (getOptionByCode(o.getCodeOption()) != null) return false;
        options.add(o);
        return true;
    }

    public static List<Option> getAllOptions() {
        return options;
    }

    public static Option getOptionByCode(int code) {
        return options.stream().filter(o -> o.getCodeOption() == code).findFirst().orElse(null);
    }

    public static List<Option> getOptionsByDomain(String domaine) {
        return options.stream().filter(o -> o.getDomaine().equalsIgnoreCase(domaine)).collect(Collectors.toList());
    }

    public static boolean updateOption(Option newOption) {
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getCodeOption() == newOption.getCodeOption()) {
                options.set(i, newOption);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteOption(int code) {
        return options.removeIf(o -> o.getCodeOption() == code);
    }

    // --- Etudiant methods ---

    public static boolean addEtudiant(Etudiant e) {
        if (getEtudiantById(e.getIdentifiant()) != null) return false;
        
        // Ensure option exists if provided
        if (e.getOption() != null) {
            Option existingOption = getOptionByCode(e.getOption().getCodeOption());
            if (existingOption == null) {
                return false; // Cannot add etudiant with non-existent option
            }
            e.setOption(existingOption);
        }
        
        etudiants.add(e);
        return true;
    }

    public static List<Etudiant> getAllEtudiants() {
        return etudiants;
    }

    public static Etudiant getEtudiantById(String id) {
        return etudiants.stream().filter(e -> e.getIdentifiant().equals(id)).findFirst().orElse(null);
    }

    public static boolean updateEtudiant(Etudiant newE) {
        for (int i = 0; i < etudiants.size(); i++) {
            if (etudiants.get(i).getIdentifiant().equals(newE.getIdentifiant())) {
                etudiants.set(i, newE);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteEtudiant(String id) {
        return etudiants.removeIf(e -> e.getIdentifiant().equals(id));
    }

    public static List<Etudiant> getEtudiantsByOptionCode(int codeOption) {
        return etudiants.stream()
                .filter(e -> e.getOption() != null && e.getOption().getCodeOption() == codeOption)
                .collect(Collectors.toList());
    }
}
