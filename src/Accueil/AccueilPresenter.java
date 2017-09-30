package Accueil;

import Main.Dat;

import javax.swing.*;

public class AccueilPresenter {

    private IAccueil view;

    AccueilPresenter(IAccueil accueilView){
        this.view = accueilView;
    }

    public void loadEnseignant(){
        if (Dat.arrayEnseignants != null && !Dat.arrayEnseignants.isEmpty() && Dat.arrayEnseignants.get(0)!=null){
            Dat.enseignant = Dat.arrayEnseignants.get(0);
        } else {
            JOptionPane.showMessageDialog(null,"Aucun enseignant n'a été créé, contactez l'administrateur de l'application");
        }

    }
}
