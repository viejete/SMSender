package sample;

public class SMS {

    private int idSmsClient;
    private Client client;
    private String texto;
    private String fechaEnvio;

    public SMS() {

    }

    public int getIdSmsClient() {
        return idSmsClient;
    }

    public void setIdSmsClient(int idSmsClient) {
        this.idSmsClient = idSmsClient;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
}
