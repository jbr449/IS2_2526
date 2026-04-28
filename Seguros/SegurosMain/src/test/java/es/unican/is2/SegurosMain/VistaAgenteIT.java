package es.unican.is2.SegurosMain;

import static org.assertj.core.api.Assertions.assertThat;

import javax.swing.JButton;
import javax.swing.text.JTextComponent;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.unican.is2.SegurosBusiness.GestionSeguros;
import es.unican.is2.SegurosCommon.IClientesDAO;
import es.unican.is2.SegurosCommon.ISegurosDAO;
import es.unican.is2.SegurosDAOH2.ClientesDAO;
import es.unican.is2.SegurosDAOH2.SegurosDAO;
import es.unican.is2.SegurosGUI.VistaAgente;

class VistaAgenteIT {

    private FrameFixture window;

    @BeforeAll
    static void setUpRepaintManager() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    void setUp() {
        IClientesDAO clientes = new ClientesDAO();
        ISegurosDAO seguros = new SegurosDAO();
        GestionSeguros negocio = new GestionSeguros(clientes, seguros);

        VistaAgente frame = GuiActionRunner.execute(() -> new VistaAgente(negocio, negocio, negocio));
        window = new FrameFixture(frame);
        window.show();
    }

    @AfterEach
    void tearDown() {
        if (window != null) {
            window.cleanUp();
        }
    }

    @Test
    void consultaClienteExistenteMuestraDatosYSeguros() {
        consultarCliente("11111111A");

        window.textBox("txtNombreCliente").requireText("Juan");
        window.textBox("txtTotalCliente").requireText("1820.0");

        String[] seguros = window.list("listSeguros").contents();
        assertThat(seguros).hasSize(3);
        assertThat(seguros).contains("1111AAA TERCEROS", "1111BBB TODO_RIESGO", "1111CCC TERCEROS");
    }

    @Test
    void consultaClienteInexistenteMuestraError() {
        consultarCliente("99999999Z");

        window.textBox("txtNombreCliente").requireText("Cliente no encontrado");
        window.textBox("txtTotalCliente").requireText("");
        window.list("listSeguros").requireItemCount(0);
    }

    private void consultarCliente(String dni) {
        JTextComponent campoDni = window.textBox("txtDniCliente").target();
        JButton botonBuscar = window.button("btnBuscar").target();
        GuiActionRunner.execute(() -> {
            campoDni.setText(dni);
            botonBuscar.doClick();
            return null;
        });
    }
}
