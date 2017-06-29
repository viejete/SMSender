package sample;

import java.sql.*;
import java.util.ArrayList;

public class DAO {

    private Connection connection;

    public DAO() {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://192.168.31.3/sms_history?useServerPrepStmts=true" , "egregia" , "Egregia");
            connection.setAutoCommit(false);

            System.out.println("Conexion abierta con la base de datos...");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Client> getClienteByNum (String number) {
        // Sacar todos los clientes de la base de datos segun numero

        try {

            ArrayList<Client> clients = new ArrayList<>();

            Statement statement = connection.createStatement();

            String query = "SELECT * FROM CLIENTE WHERE telefono LIKE '%" + number +"%';";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // id_cliente , nombre , apellido , dni , telefono , direccion , email , sexo , fecha_alta

                Client client = new Client();

                int idCliente = resultSet.getInt(1);
                String nombre = resultSet.getString(2);
                String apellido = resultSet.getString(3);
                String dni = resultSet.getString(4);
                String telefono = resultSet.getString(5);
                String direccion = resultSet.getString(6);
                String email = resultSet.getString(7);
                char sexo = 0;
                try {
                    sexo = resultSet.getString(8).charAt(0);
                } catch (NullPointerException e) {
                    System.err.println("Null char sexo!");
                }
                String fechaAlta = resultSet.getString(9);

                System.out.println("nombre: " + nombre + ", apelldio: " + apellido + ", telefono: " + telefono);

                client.setId_cliente(idCliente);
                if (nombre != null) {
                    client.setNombre(nombre);
                }
                if (apellido != null) {
                    client.setApellido(apellido);
                }
                if (dni != null) {
                    client.setDni(dni);
                }
                client.setTelefono(telefono);
                if (direccion != null) {
                    client.setDireccion(direccion);
                }
                if (email != null) {
                    client.setEmail(email);
                }
                if (sexo != 0) {
                    client.setSexo(sexo);
                }
                client.setFechaAlta(fechaAlta);

                clients.add(client);
            }

            resultSet.close();

            return clients;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Client> getClienteByName (String apellido) {
        // Sacar todos los clientes de la base de datos segun apellido

        try {

            ArrayList<Client> clients = new ArrayList<>();

            Statement statement = connection.createStatement();

            String query = "SELECT * FROM CLIENTE WHERE apellido LIKE '%" + apellido + "%';";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // id_cliente , nombre , apellido , dni , telefono , direccion , email , sexo , fecha_alta

                Client client = new Client();

                int idCliente = resultSet.getInt(1);
                String nombre = resultSet.getString(2);
                String apellidoData = resultSet.getString(3);
                String dni = resultSet.getString(4);
                String telefono = resultSet.getString(5);
                String direccion = resultSet.getString(6);
                String email = resultSet.getString(7);
                char sexo = 0;
                try {
                    sexo = resultSet.getString(8).charAt(0);
                } catch (NullPointerException e) {
                    System.err.println("Null char sexo!");
                }
                String fechaAlta = resultSet.getString(9);

                System.out.println("nombre: " + nombre + ", apelldio: " + apellido + ", telefono: " + telefono);

                client.setId_cliente(idCliente);
                if (nombre != null) {
                    client.setNombre(nombre);
                }
                if (apellido != null) {
                    client.setApellido(apellidoData);
                }
                if (dni != null) {
                    client.setDni(dni);
                }
                client.setTelefono(telefono);
                if (direccion != null) {
                    client.setDireccion(direccion);
                }
                if (email != null) {
                    client.setEmail(email);
                }
                if (sexo != 0) {
                    client.setSexo(sexo);
                }
                client.setFechaAlta(fechaAlta);

                clients.add(client);
            }

            resultSet.close();

            return clients;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<SMS> getHistorialByNum (String numero) {

        try {

            ArrayList<SMS> smeses = new ArrayList<>();

            Statement statement = connection.createStatement();

            String query = "SELECT * FROM sms_cliente S JOIN cliente C ON S.id_cliente = C.id_cliente WHERE C.telefono = '" + numero + "';";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // SMS: id_sms_cliente, id_cliente, texto, fecha_envio
                // Client: id_cliente, nombre, apellido, dni, telefono, direccion, email, sexo, fecha_alta

                SMS sms = new SMS();
                Client client = new Client();

                int id_sms_cliente = resultSet.getInt(1);
                int id_cliente = resultSet.getInt(2);
                String body = resultSet.getString(3);
                String fechaEnvio = resultSet.getString(4);

                String nombre = resultSet.getString(6);
                String apellido = resultSet.getString(7);
                String dni = resultSet.getString(8);
                String telefono = resultSet.getString(9);
                String direccion = resultSet.getString(10);
                String email = resultSet.getString(11);
                String sexo = resultSet.getString(12);
                String fechaAlta = resultSet.getString(13);


                System.out.println("body: " + body + ", fechaEnvio: " + fechaEnvio);
                System.out.println("nombre: " + nombre + ", sexo: " + sexo + ", telefono: " + telefono + ", fecha_alta: " + fechaAlta);

                client.setId_cliente(id_cliente);
                if (nombre != null) {
                    client.setNombre(nombre);
                }
                if (apellido != null) {
                    client.setApellido(apellido);
                }
                if (dni != null) {
                    client.setDni(dni);
                }
                client.setTelefono(telefono);
                if (direccion != null) {
                    client.setDireccion(direccion);
                }
                if (email != null) {
                    client.setEmail(email);
                }
                if (sexo != null) {
                    client.setSexo(sexo.charAt(0));
                }
                client.setFechaAlta(fechaAlta);

                sms.setIdSmsClient(id_sms_cliente);
                sms.setClient(client);
                if (body != null){
                    sms.setTexto(body);
                }
                sms.setFechaEnvio(fechaEnvio);

                smeses.add(sms);
            }

            resultSet.close();

            return smeses;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String> getPredefinidos() {
        // Sacar todos los sms predefinidos

        try {

            ArrayList<String> predefinidos = new ArrayList<>();

            Statement statement = connection.createStatement();

            String query = "SELECT texto FROM SMS;";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // texto

                String texto = resultSet.getString(1);

                predefinidos.add(texto);

            }

            resultSet.close();

            return predefinidos;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertClient (Client client) {

        try {

            PreparedStatement psClient = connection.prepareStatement("INSERT INTO CLIENTE (nombre , apellido , dni , telefono , direccion , email , sexo) VALUES (?,?,?,?,?,?,?);");
            psClient.clearParameters();

            psClient.setString(1 , client.getNombre());
            psClient.setString(2 , client.getApellido());
            psClient.setString(3 , client.getDni());
            psClient.setString(4 , client.getTelefono());
            psClient.setString(5 , client.getDireccion());
            psClient.setString(6 , client.getEmail());
            psClient.setString(7 , String.valueOf(client.getSexo()));

            psClient.executeUpdate();

            connection.commit();

            System.out.println("Successful!");

            return true;

        } catch (SQLException e) {
            System.err.println("Fallo al introducir un cliente!");
            e.printStackTrace();
        }

        return false;
    }

    public boolean insertMessage (SMS sms) {

        try {

            PreparedStatement psSMS = connection.prepareStatement("INSERT INTO sms_cliente (id_cliente , texto) VALUES (?,?);");
            psSMS.clearParameters();

            psSMS.setInt(1 , sms.getClient().getId_cliente());
            psSMS.setString(2 , sms.getTexto());

            psSMS.executeUpdate();

            connection.commit();

            return true;

        } catch (SQLException e) {
            System.err.println("Fallo al introducir un cliente!");
            e.printStackTrace();
        }

        return false;
    }

    public boolean insertPredefinido (String body) {

        try {

            PreparedStatement psPred = connection.prepareStatement("INSERT INTO sms (texto) VALUES (?);");
            psPred.clearParameters();

            psPred.setString(1 , body);

            psPred.executeUpdate();

            connection.commit();

            return true;

        } catch (SQLException e) {
            System.err.println("Fallo al introducir un predefinido!");
            e.printStackTrace();
        }

        return false;
    }
}
