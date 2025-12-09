package org.example.LubrimaxPOO.actions;

import org.openxava.actions.SaveAction;

public class SaveVentaAction extends SaveAction {
    @Override
    public void execute() throws Exception {
        // Delegate to OpenXava's SaveAction; entity callbacks will create Movimientos
        super.execute();
        // Optionally you can add post-save logic or messages here
    }
}

