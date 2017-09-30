package Consult;

import tablesBDD.*;

import java.util.List;

public interface IConsult {

    void showComboEvaluations(List<Eval> comboEvals);

    void showComboEleves(List<Eleve> comboEleves);

    void showComboClasses(List<Classe> comboClasses);

    void showComboMatieres(List<Matiere> comboMatieres);

    void showComboDomaines(List<Domaine> comboDomaines);

    void updateAllContent(List<Objectif> objectifs, Eleve eleve);

    void updateAllContent(List<Objectif> objectifs, Classe classe);

    void nothingToShow();

    void revalidate();

    void setUpdating(boolean b);

    boolean isUpdating();
}
