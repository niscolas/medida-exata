package br.cefetmg.inf.medidaexata.model.domain;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class Colecao {
    private String codigo;
    private List<DocumentReference> documentos;
    private DocumentReference pai;

    public Colecao(String codigo, List<DocumentReference> documentos, DocumentReference pai) {
        this.codigo = codigo;
        this.documentos = documentos;
        this.pai = pai;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<DocumentReference> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentReference> documentos) {
        this.documentos = documentos;
    }

    public DocumentReference getPai() {
        return pai;
    }

    public void setPai(DocumentReference pai) {
        this.pai = pai;
    }
}