package utils;

import models.TemaConfig;

public class TemaHelper {
    public static TemaConfig getConfig(int tema) {
        TemaConfig config = new TemaConfig();

        switch (tema) {
            case 1:
            default:
                config.setColorPrimario("--tema1-color-primario");
                config.setColorSecundario("--tema1-color-secundario");
                config.setColorTerciario("--tema1-color-terciario");
                config.setColorTerciarioJS("#4A6572");
                config.setColorCuaternario("--tema1-color-cuaternario");
                config.setColorTexto("--tema1-color-texto");
                config.setColorTextoJS("#ecf0f1");
                config.setColorDisabled("--tema1-color-disabled");
                config.setImageAdd("images/add.png");
                config.setImageConfigIcon("images/config-icon.png");
                config.setImageDeleteSolve("images/delete-solve.png");
                config.setImageDnf("images/dnf.png");
                config.setImageExport("images/export.png");
                config.setImageImport("images/import.png");
                config.setImageInfo("images/info.png");
                config.setImageLogout("images/logout.png");
                config.setImageMasDos("images/mas_dos.png");
                config.setImageNext("images/next.png");
                config.setImagePersonalizar("images/personalizar.png");
                config.setImagePreferences("images/preferences.png");
                config.setImagePrevius("images/previus.png");
                config.setImageRestart("images/restart.png");
                break;
            case 2:
                config.setColorPrimario("--tema2-color-primario");
                config.setColorSecundario("--tema2-color-secundario");
                config.setColorTerciario("--tema2-color-terciario");
                config.setColorTerciarioJS("#99c3ff");
                config.setColorCuaternario("--tema2-color-cuaternario");
                config.setColorTexto("--tema2-color-texto");
                config.setColorTextoJS("#000000");
                config.setColorDisabled("--tema2-color-disabled");
                config.setImageAdd("images/add_negro.png");
                config.setImageConfigIcon("images/config-icon_negro.png");
                config.setImageDeleteSolve("images/delete-solve_negro.png");
                config.setImageDnf("images/dnf_negro.png");
                config.setImageExport("images/export_negro.png");
                config.setImageImport("images/import_negro.png");
                config.setImageInfo("images/info_negro.png");
                config.setImageLogout("images/logout_negro.png");
                config.setImageMasDos("images/mas_dos_negro.png");
                config.setImageNext("images/next_negro.png");
                config.setImagePersonalizar("images/personalizar_negro.png");
                config.setImagePreferences("images/preferences_negro.png");
                config.setImagePrevius("images/previus_negro.png");
                config.setImageRestart("images/restart_negro.png");
                break;
        }

        return config;
    }
}