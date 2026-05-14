package es.unican.is2.practica6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestionTransportes {

    private final List<Conductor> cs = new ArrayList<>();

    public Conductor buscaConductor(String dni) {
        for (Conductor c : cs) {
            if (c.dni().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    public boolean anhadeConductor(String dni, String nombre, String apellido1, String apellido2, String direccion) {
        if (buscaConductor(dni) != null) {
            return false;
        }
        cs.add(new Conductor(dni, nombre, apellido1, apellido2, direccion));
        return true;
    }

    public List<Conductor> conductores() {
        return Collections.unmodifiableList(cs);
    }

    public List<Conductor> mejoresConductores() {
        List<Conductor> resultado = new ArrayList<>();
        double maxSueldo = Double.NEGATIVE_INFINITY;

        for (Conductor conductor : cs) {
            double sueldo = conductor.sueldo();
            if (sueldo > maxSueldo) {
                maxSueldo = sueldo;
                resultado.clear();
                resultado.add(conductor);
            } else if (sueldo == maxSueldo) {
                resultado.add(conductor);
            }
        }
        return resultado;
    }
}
