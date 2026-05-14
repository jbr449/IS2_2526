# Practica 6 - Transportes

Proyecto Maven con la aplicacion de gestion de transportes indicada en el UML.

## Decisiones aplicadas

- Se normaliza la clase `GestionTransportes`, tal como aparece en el UML. En Windows no se puede mantener a la vez el alias `gestionTransportes` porque el sistema de archivos no diferencia mayusculas y minusculas.
- Se eliminan dependencias externas a `fundamentos` usando una interfaz de consola compilable.
- Se encapsulan las colecciones internas devolviendo vistas no modificables.
- Se extrae el calculo de mejores conductores al modelo para poder probarlo.
- La interfaz `GestionTransportesGUI` se excluye del informe JaCoCo porque es una capa interactiva.
- Se mantienen los nombres de metodos originales (`dni`, `apellido2`, `horas`, `ton`) y se anaden getters equivalentes donde ayudan a la interfaz.

## Comandos

```powershell
mvn test
```

El informe de cobertura JaCoCo se genera en `target/site/jacoco/index.html`.
