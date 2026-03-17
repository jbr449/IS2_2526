package es.unican.is2.SegurosGUI;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import es.unican.is2.SegurosCommon.Cliente;
import es.unican.is2.SegurosCommon.DataAccessException;
import es.unican.is2.SegurosCommon.IGestionClientes;
import es.unican.is2.SegurosCommon.IGestionSeguros;
import es.unican.is2.SegurosCommon.IInfoSeguros;
import es.unican.is2.SegurosCommon.Seguro;

@SuppressWarnings("serial")
public class VistaAgente extends JFrame {

    private JPanel contentPane;
    private JTextField txtDniCliente;
    private JTextField txtTotalCliente;
    private JTextField txtNombreCliente;
    private JList<String> listSeguros;
    private DefaultListModel<String> listModel;
    private JButton btnBuscar;

    @SuppressWarnings("unused")
    private final IGestionClientes clientes;
    @SuppressWarnings("unused")
    private final IGestionSeguros seguros;
    private final IInfoSeguros info;

    public VistaAgente(IGestionClientes clientes, IGestionSeguros seguros, IInfoSeguros info) {
        this.clientes = clientes;
        this.seguros = seguros;
        this.info = info;
        init();
    }

    private void init() {
        setTitle("Gestión de Seguros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 341);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        listModel = new DefaultListModel<>();

        txtTotalCliente = new JTextField();
        txtTotalCliente.setBounds(230, 251, 180, 20);
        txtTotalCliente.setColumns(10);
        txtTotalCliente.setEditable(false);
        txtTotalCliente.setName("txtTotalCliente");
        contentPane.add(txtTotalCliente);

        JLabel lblTotalCliente = new JLabel("Total A Pagar");
        lblTotalCliente.setBounds(137, 254, 180, 14);
        contentPane.add(lblTotalCliente);

        listSeguros = new JList<>();
        listSeguros.setBounds(230, 98, 180, 116);
        listSeguros.setBorder(new LineBorder(new Color(0, 0, 0)));
        listSeguros.setModel(listModel);
        contentPane.add(listSeguros);

        JLabel lblSeguros = new JLabel("Seguros");
        lblSeguros.setBounds(149, 93, 65, 14);
        contentPane.add(lblSeguros);

        JLabel lblNombreCliente = new JLabel("Nombre");
        lblNombreCliente.setBounds(155, 54, 65, 14);
        contentPane.add(lblNombreCliente);

        txtNombreCliente = new JTextField();
        txtNombreCliente.setBounds(230, 51, 180, 20);
        txtNombreCliente.setColumns(10);
        txtNombreCliente.setEditable(false);
        txtNombreCliente.setName("txtNombreCliente");
        contentPane.add(txtNombreCliente);

        JLabel lblDatosCliente = new JLabel("Datos Cliente");
        lblDatosCliente.setBounds(230, 11, 149, 14);
        contentPane.add(lblDatosCliente);

        txtDniCliente = new JTextField();
        txtDniCliente.setBounds(10, 51, 113, 20);
        txtDniCliente.setColumns(10);
        txtDniCliente.setName("txtDniCliente");
        contentPane.add(txtDniCliente);

        JLabel lblDniCliente = new JLabel("DNI Cliente");
        lblDniCliente.setBounds(21, 27, 139, 14);
        lblDniCliente.setName("lblDniCliente");
        contentPane.add(lblDniCliente);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(21, 122, 89, 23);
        btnBuscar.setName("btnBuscar");
        btnBuscar.addActionListener(this::buscarCliente);
        contentPane.add(btnBuscar);
    }

    private void buscarCliente(ActionEvent event) {
        rellenaDatosCliente(txtDniCliente.getText().trim());
    }

    private void rellenaDatosCliente(String dni) {
        try {
            Cliente c = info.cliente(dni);
            if (c == null) {
                mostrarError("Cliente no encontrado");
                return;
            }

            txtNombreCliente.setText(c.getNombre());
            txtTotalCliente.setText(Double.toString(info.totalSegurosCliente(dni)));
            listModel.clear();
            for (Seguro seguro : c.getSeguros()) {
                listModel.addElement(seguro.getMatricula() + " " + seguro.getCobertura());
            }
        } catch (DataAccessException e) {
            mostrarError("Error en BBDD");
        }
    }

    private void mostrarError(String mensaje) {
        txtNombreCliente.setText(mensaje);
        txtTotalCliente.setText("");
        listModel.clear();
    }
}
