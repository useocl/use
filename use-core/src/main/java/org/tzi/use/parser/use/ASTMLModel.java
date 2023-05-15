package org.tzi.use.parser.use;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MMLModel;
import org.tzi.use.uml.mm.MModel;

import java.util.ArrayList;
import java.util.List;

public class ASTMLModel extends AST {

    private final Token fName;
    private final List<ASTModel> fModels;

    public ASTMLModel(Token name) {
        fName = name;
        fModels = new ArrayList<>();
    }

    public void addModel(ASTModel model) {
        fModels.add(model);
    }

    public MMLModel gen(Context ctx) {
        MMLModel mlmodel = ctx.modelFactory().createMLModel(fName.getText());
        mlmodel.setFilename(ctx.filename());
        ctx.setMLModel(mlmodel);

        for (ASTModel model : fModels) {
            try{
                mlmodel.addModel(model.gen(ctx));
            }catch(Exception e) { //TODO: add custom excepetions
                System.out.println(e.getMessage());
            }
        }


        return mlmodel;
    }

    public String toString() {
        return "(" + fName + ")";
    }



}
