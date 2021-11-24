package controller;

import model.Level;

import java.util.ArrayList;

public class UndoManager {

    private Level l;

    private final ArrayList<Action> edits;
    private int index;

    public UndoManager(Level level) {
        this.edits = new ArrayList<>();
        this.l = level;
        index = -1;
    }

    // Test pour savoir si le joueur peut undo()
    public boolean canUndo() {
        return edits.size() > 0 && index > -1;
    }

    // Test pour savoir si le joueur peut redo()
    public boolean canRedo() {
        return index < edits.size() - 1 && edits.size() > 0;
    }

    // Ajoute une action dans edits, efface les actions annulées
    public void recordAction(Action a) {
        int size = edits.size();
        for (int i = index; i < size - 1; i++) {
            edits.remove(edits.size() - 1);
        }
        edits.add(a);
        index = edits.size() - 1;
    }

    // Ré-effectue la dernière action
    public void undo() {
        if (canUndo()) {
            Action a = edits.get(index);
            applyAction(a.target, a.source);
            index--;
        }
    }

    // Annule la dernière action ré-effectuée
    public void redo() {
        if (canRedo()) {
            Action a = edits.get(index + 1);
            applyAction(a.source, a.target);
            index++;
        }
    }

    // Fonction pour appliquer une Action sur le model
    private void applyAction(String source, String target) {
        if (source.charAt(0) == 'P') {
            if (target.charAt(0) == 'P') {
                int xSource = source.charAt(2) - '0';
                int ySource = source.charAt(1) - '0';
                int xTarget = target.charAt(2) - '0';
                int yTarget = target.charAt(1) - '0';

                l.getCurrentState()[yTarget][xTarget] = l.getCurrentState()[ySource][xSource];
                l.getCurrentState()[ySource][xSource] = ".";
            }

            if (target.charAt(0) == 'R') {
                int xSource = source.charAt(2) - '0';
                int ySource = source.charAt(1) - '0';
                int index = Integer.parseInt(target.substring(1));
                l.getCurrentState()[ySource][xSource] = ".";
                l.getRessources()[index]++;
            }
        } else if (source.charAt(0) == 'R' && target.charAt(0) == 'P') {
            int index = Integer.parseInt(source.substring(1));
            int xTarget = target.charAt(2) - '0';
            int yTarget = target.charAt(1) - '0';

            switch (index) {
                case 0 : l.getCurrentState()[yTarget][xTarget] = "C0"; break;
                case 1 : l.getCurrentState()[yTarget][xTarget] = "O0"; break;
                case 2 : l.getCurrentState()[yTarget][xTarget] = "L0"; break;
                case 3 : l.getCurrentState()[yTarget][xTarget] = "L1"; break;
                case 4 : l.getCurrentState()[yTarget][xTarget] = "T1"; break;
                case 5 : l.getCurrentState()[yTarget][xTarget] = "T2"; break;
                case 6 : l.getCurrentState()[yTarget][xTarget] = "T0"; break;
                case 7 : l.getCurrentState()[yTarget][xTarget] = "T3"; break;
                case 8 : l.getCurrentState()[yTarget][xTarget] = "F0"; break;
                case 9 : l.getCurrentState()[yTarget][xTarget] = "F1"; break;
                case 10 : l.getCurrentState()[yTarget][xTarget] = "F3"; break;
                case 11 : l.getCurrentState()[yTarget][xTarget] = "F2"; break;
            }
            l.getRessources()[index]--;
        }
    }
}
