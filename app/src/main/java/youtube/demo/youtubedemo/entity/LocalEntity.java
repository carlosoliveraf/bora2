package youtube.demo.youtubedemo.entity;

import java.io.Serializable;

/**
 * Created by oliverace on 19/12/2016.
 */

public class LocalEntity implements Serializable {

    private String _id;
    private String name;
    private String email;
    private String funcionamento;
    private String url;
    private String telefone;
    private String endereco;
    private Double lat;
    private Double longit;
    private Double produto;
    private Double lotacao;
    private Double precos;
    private Double atendimento;

    public LocalEntity(String name) {
        this.name = name;
    }

    public LocalEntity(String _id, String name, String email, String funcionamento, String url, String telefone, String endereco, Double lat, Double longit) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.funcionamento = funcionamento;
        this.url = url;
        this.telefone = telefone;
        this.endereco = endereco;
        this.lat = lat;
        this.longit = longit;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFuncionamento() {
        return funcionamento;
    }

    public void setFuncionamento(String funcionamento) {
        this.funcionamento = funcionamento;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLongit() {
        return longit;
    }

    public void setLongit(Double longit) {
        this.longit = longit;
    }

    public Double getProduto() {
        return produto;
    }

    public void setProduto(Double produto) {
        this.produto = produto;
    }

    public Double getLotacao() {
        return lotacao;
    }

    public void setLotacao(Double lotacao) {
        this.lotacao = lotacao;
    }

    public Double getPrecos() {
        return precos;
    }

    public void setPrecos(Double precos) {
        this.precos = precos;
    }

    public Double getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Double atendimento) {
        this.atendimento = atendimento;
    }
}


