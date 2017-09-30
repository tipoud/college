package Evaluations;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import DAO.DAO;
import Main.Dat;
import tablesBDD.Eleve;
import tablesBDD.Eval;
import tablesBDD.Note;
import tablesBDD.Objectif;

public class MyCaseNote extends JComboBox<String> implements ActionListener {

    private static final long serialVersionUID = 1L;
    private List<MyCaseNote> arrNotes;
    private int numberOfObjectifs;
    private int numberOfEleves;
    private Objectif objectif = new Objectif();
    private Eleve eleve = new Eleve();
    private Eval eval = new Eval();
    private ArrayList<Character> charac = new ArrayList<Character>();
    private Note note = new Note();
    private int id;

    private DAO<Note> noteDAO = Dat.noteDAO;

    public MyCaseNote() {
        super();
        this.setPreferredSize(new Dimension(70, 20));
        String[] tab = {"NE", "1", "2", "3", "4", "Absent"};
        for (String string : tab) {
            this.addItem(string);
        }
        this.addItemListener(new ItemChangedListener());

        charac.add('1');
        charac.add('2');
        charac.add('3');
        charac.add('4');
        charac.add('A');
        charac.add('a');
        charac.add('N');
        charac.add('n');

        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(note);
                if (!EvaluationPanel.bas) {
                    if (charac.contains(e.getKeyChar())) {
                        try {
                            System.out.println(e.getKeyChar());
                            Robot robot = new Robot();
                            robot.keyPress(KeyEvent.VK_TAB);
                            robot.keyRelease(KeyEvent.VK_TAB);
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    int pos = id;

                    pos = pos + numberOfObjectifs;
                    System.out.println("saisie size :"+ numberOfObjectifs);

                    if(pos>= numberOfObjectifs* numberOfEleves)
                    {
                        pos = pos + 1 - (numberOfObjectifs * numberOfEleves);
                        if(pos == numberOfObjectifs) pos=0;
                    }
                    arrNotes.get(pos).requestFocus();
                }

            }
        });
    }

    public MyCaseNote(Eleve eleve, int numberOfObjectifs, int numberOfEleves, List<MyCaseNote> arrNotes) {
        this();
        this.eleve = eleve;
        this.numberOfEleves = numberOfEleves;
        this.numberOfObjectifs = numberOfObjectifs;
        this.arrNotes = arrNotes;
        note = new Note();
        note.setIdEleve(eleve.getIdEleve());
    }

    public MyCaseNote(Objectif comp, Eleve eleve, Eval eval, int j) {
        this();
        this.eleve = eleve;
        this.objectif = comp;
        this.eval = eval;
        this.id = j;


        note = new Note(eleve.getIdEleve(), eval.getIdEval(), 0, comp.getIdObjectif(), false, 0);


    }


    public MyCaseNote(Note note, int j) {
        this();
        this.note = note;
        this.eleve = Eleve.get(note.getIdEleve());
        this.objectif = Objectif.get(note.getIdObjectif());
        this.eval = Eval.get(note.getIdEval());
        this.id = j;

        if (note.getVal() == 0) {
            if (note.isAbsent()) {
                this.setSelectedItem("Absent");
            } else
                this.setSelectedItem("NE");
        } else {
            this.setSelectedItem(Integer.toString(note.getVal()));
        }

    }

    public Objectif getObjectif() {
        return objectif;
    }

    public void setObjectif(Objectif comp) {
        this.objectif = comp;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Eval getEval() {
        return eval;
    }

    public void setEval(Eval eval) {
        this.eval = eval;
    }

    public DAO<Note> getNoteDAO() {
        return noteDAO;
    }

    public void setNoteDAO(DAO<Note> noteDAO) {
        this.noteDAO = noteDAO;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eleve == null) ? 0 : eleve.hashCode());
        result = prime * result + ((eval == null) ? 0 : eval.hashCode());
        result = prime * result + ((objectif == null) ? 0 : objectif.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MyCaseNote other = (MyCaseNote) obj;
        if (eleve == null) {
            if (other.eleve != null)
                return false;
        } else if (!eleve.equals(other.eleve))
            return false;
        if (eval == null) {
            if (other.eval != null)
                return false;
        } else if (!eval.equals(other.eval))
            return false;
        if (objectif == null) {
            if (other.objectif != null)
                return false;
        } else if (!objectif.equals(other.objectif))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "MyCaseNote [objectif=" + objectif + ", eleve=" + eleve + ", eval=" + eval + ", note=" + note + "]";
    }

}
