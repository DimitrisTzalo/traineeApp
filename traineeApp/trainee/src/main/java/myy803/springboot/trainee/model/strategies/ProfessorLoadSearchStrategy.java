package myy803.springboot.trainee.model.strategies;

import myy803.springboot.trainee.model.Professor;
import myy803.springboot.trainee.model.TraineePosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfessorLoadSearchStrategy implements ProfessorSearchStrategy{
    @Override
    public List<TraineePosition> search(Professor professor, List<TraineePosition> positions) {
        List<TraineePosition> result = new ArrayList<>();

        if (professor == null || positions == null || professor.getSupervisedPositions() == null) {
            return result;
        }

        int currentLoad = professor.getSupervisedPositions().size();

        // Δημιουργία προσωρινού πίνακα φορτίου
        Map<TraineePosition, Integer> positionLoadMap = new HashMap<>();
        int simulatedLoad = currentLoad;

        for (TraineePosition pos : positions) {
            if (pos != null) {
                positionLoadMap.put(pos, simulatedLoad);
                simulatedLoad++; // υποθέτουμε ότι κάθε θέση προσθέτει 1 φορτίο
            }
        }

        while (!positionLoadMap.isEmpty()) {
            TraineePosition minPos = null;
            int minLoad = Integer.MAX_VALUE;

            for (Map.Entry<TraineePosition, Integer> entry : positionLoadMap.entrySet()) {
                if (entry.getValue() < minLoad) {
                    minLoad = entry.getValue();
                    minPos = entry.getKey();
                }
            }

            if (minPos != null) {
                result.add(minPos);
                positionLoadMap.remove(minPos);
            }
        }

        return result;
    }

}
