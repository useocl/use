package org.tzi.use.parser.use;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.uml.mm.MMultiModel;

import java.util.ArrayList;
import java.util.List;

public class ASTMultiModel extends AST {

    private final Token fName;
    private final List<ASTModel> fModels;

    public ASTMultiModel(Token name) {
        fName = name;
        fModels = new ArrayList<>();
    }

    public void addModel(ASTModel model) {
        fModels.add(model);
    }

    public MMultiModel gen(Context ctx) {
        MMultiModel mMultiModel = ctx.modelFactory().createMultiModel(fName.getText());
        mMultiModel.setFilename(ctx.filename());
        ctx.setMultiModel(mMultiModel);

        for (ASTModel model : fModels) {
            try{
                mMultiModel.addModel(model.gen(ctx));
            }catch(Exception e) { //TODO: add custom excepetions
                System.out.println(e.getMessage());
            }
        }


        return mMultiModel;
    }

    public String toString() {
        return "(" + fName + ")";
    }



}
