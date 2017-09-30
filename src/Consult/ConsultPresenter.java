package Consult;

import Main.Dat;
import tablesBDD.*;

import java.util.ArrayList;
import java.util.List;

public class ConsultPresenter {

    private IConsult view;
    private Matiere matiere;
    private Eleve eleve;
    private Eval evaluation;
    private Classe classe;
    private Domaine domaine;

    private List<Matiere> comboMatieres = new ArrayList<>();
    private List<Eleve> comboEleves = new ArrayList<>();
    private List<Classe> comboClasses = new ArrayList<>();
    private List<Eval> comboEvals = new ArrayList<>();
    private List<Domaine> comboDomaines = new ArrayList<>();


    boolean allMatieres = true;
    private boolean allEleves = true;
    boolean allEvaluations = true;
    private boolean allDomaines = true;
    boolean evaluatedOnly = true;


    ConsultPresenter(IConsult view) {
        this.view = view;
    }

    public void update(Matiere matiere) {
        if (view.isUpdating()) {
            return;
        }
        allMatieres = "Toutes".equals(matiere.getNom());
        this.matiere = matiere;
        updateAllContent();
        if(view != null){
            view.revalidate();
        }
    }

    public void update(Eleve eleve) {
        if (view.isUpdating()) {
            return;
        }
        allEleves = "Tous".equals(eleve.getNom());
        this.eleve = eleve;
        updateAllContent();
        if(view != null){
            view.revalidate();
        }
    }

    public void update(Eval eval) {
        if (view.isUpdating()) {
            return;
        }
        allEvaluations = "Toutes les évaluations".equals(eval.getNom());
        this.evaluation = eval;
        updateAllContent();
        if(view != null){
            view.revalidate();
        }
    }

    public void update(Classe classe) {
        if (view.isUpdating()) {
            return;
        }
        this.classe = classe;
        updateComboEleves();
        updateComboEvals();
        updateAllContent();
        if(view != null){
            view.revalidate();
        }
    }

    public void update(Domaine domaine) {
        if (view.isUpdating()) {
            return;
        }
        this.domaine = domaine;
        allDomaines = "Tous".equals(domaine.getNom());
        updateAllContent();
        if(view != null){
            view.revalidate();
        }
    }

    void udpateEvaluatedOnly(boolean selected) {
        evaluatedOnly = selected;
        updateAllContent();
    }


    void initBorder() {

        updateComboMatieres();

        updateComboDomaines();

        updateComboClasses();

        updateComboEleves();

        updateComboEvals();

        updateAllContent();

    }

    private void updateComboEvals() {
        view.setUpdating(true);
        comboEvals.clear();
        allEvaluations = true;
        if (Dat.enseignant != null && classe != null) {
            ArrayList<Eval> filteredEvals = Eval.get(Dat.enseignant.getIdEnseignant(), classe.getIdClasse());
            if (!filteredEvals.isEmpty()) {
                evaluation = null;
                comboEvals.add(new Eval());
                comboEvals.addAll(filteredEvals);
            } else {
                comboEvals.add(new Eval("Aucune évaluation disponible"));
            }
        } else {
            comboEvals.add(new Eval("Aucune évaluation disponible"));
        }
        view.showComboEvaluations(comboEvals);
        view.setUpdating(false);
    }

    private void updateComboEleves() {
        view.setUpdating(true);
        comboEleves.clear();
        allEleves = true;
        if (classe != null && !classe.getEleves().isEmpty()) {
            eleve = null;
            comboEleves.add(new Eleve());
            comboEleves.addAll(classe.getEleves());
        } else {
            comboEleves.add(new Eleve("Aucun élève disponible"));
        }
        view.showComboEleves(comboEleves);
        view.setUpdating(false);
    }

    private void updateComboClasses() {
        view.setUpdating(true);
        comboClasses.clear();
        if (!Dat.arrayClasses.isEmpty()) {
            classe = Dat.arrayClasses.get(0);
            comboClasses.addAll(Dat.arrayClasses);
        } else {
            comboClasses.add(new Classe("Aucune classe disponible"));
        }
        view.showComboClasses(comboClasses);
        view.setUpdating(false);
    }

    private void updateComboMatieres() {
        view.setUpdating(true);
        comboMatieres.clear();
        if (!Dat.arrayMatieres.isEmpty()) {
            matiere = null;
            comboMatieres.add(new Matiere());
            for (Matiere matiere : Dat.arrayMatieres) {
                if (!matiere.getObjectifs().isEmpty()) {
                    comboMatieres.add(matiere);
                }
            }
        } else {
            comboMatieres.add(new Matiere("Aucune matière disponible"));
        }
        view.showComboMatieres(comboMatieres);
        view.setUpdating(false);
    }

    private void updateComboDomaines() {
        view.setUpdating(true);
        comboDomaines.clear();
        if (!Dat.arrayDomaines.isEmpty()) {
            domaine = null;
            comboDomaines.add(new Domaine());
            comboDomaines.addAll(Dat.arrayDomaines);
        } else {
            comboDomaines.add(new Domaine("Aucun domaine disponible"));
        }
        view.showComboDomaines(comboDomaines);
        view.setUpdating(false);
    }

    private void updateAllContent() {
        List<Objectif> objectifs = new ArrayList<>();
        if (allEvaluations) {
            if (allMatieres) {
                objectifs.addAll(Dat.arrayObjectifs);
            } else {
                if (matiere != null) {
                    objectifs.addAll(matiere.getObjectifs());
                }
            }
        } else {
            objectifs.addAll(Objectif.get(evaluation));
        }

        if(!allDomaines && domaine != null){
            objectifs.removeIf(objectif -> (objectif.getIdDomaine() != domaine.getIdDomaine()));
        }

        if (evaluatedOnly) {
            ArrayList<Objectif> filteredObjectifs = new ArrayList<>();
            if (!allEleves && eleve !=null) {
                for (Objectif objectif : objectifs)
                    for (Note note : eleve.getNotes()) {
                        if (note.getIdObjectif() == objectif.getIdObjectif()) {
                            filteredObjectifs.add(objectif);
                            break;
                        }
                    }
                if (filteredObjectifs.isEmpty()) {
                    view.nothingToShow();
                } else {
                    view.updateAllContent(filteredObjectifs,eleve);
                }
            } else if (classe != null) {
                for (Objectif objectif : objectifs) {
                    boucleEleve:
                    for (Eleve eleve : classe.getEleves()) {
                        for (Note note : eleve.getNotes()) {
                            if (note.getIdObjectif() == objectif.getIdObjectif()) {
                                filteredObjectifs.add(objectif);
                                break boucleEleve;
                            }
                        }
                    }
                }
                    view.updateAllContent(filteredObjectifs,classe);
            } else {
                view.nothingToShow();
            }
        } else {
                if (!allEleves && eleve !=null) {
                    view.updateAllContent(objectifs, eleve);
                } else if (allEleves && classe !=null) {
                    view.updateAllContent(objectifs, classe);
                } else {
                    view.nothingToShow();
                }
        }

    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Eval getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Eval evaluation) {
        this.evaluation = evaluation;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }


}
