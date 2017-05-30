/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.plugin.clangd.languageserver;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.api.languageserver.exception.LanguageServerException;
import org.eclipse.che.api.languageserver.launcher.LanguageServerLauncherTemplate;
import org.eclipse.che.api.languageserver.shared.model.LanguageDescription;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;

import java.io.IOException;
import java.util.Arrays;

import static java.util.Arrays.asList;

/**
 * @author Alexander Andrienko
 */
@Singleton
public class ClangdLaunguageServerLauncher extends LanguageServerLauncherTemplate {

    private static final String LANGUAGE_ID = "clangd";
    private static final LanguageDescription description;
    private static final String[] EXTENSIONS = new String[]{"c", "cpp", "h", "cc"};//todo
    private static final String[] MIME_TYPES = new String[]{"text/x-c", "text/x-c", "text/x-h", "text/x-c"};

    private static String laungDBinary;

    @Inject
    public ClangdLaunguageServerLauncher() {
        laungDBinary = "clangd";//todo
    }

    @Override
    public LanguageDescription getLanguageDescription() {
        return description;
    }

    @Override
    protected Process startLanguageServerProcess(String projectPath) throws LanguageServerException {
        ProcessBuilder processBuilder = new ProcessBuilder(laungDBinary);
        processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE);
        try {
            return processBuilder.start();
        } catch (IOException e) {
            throw new LanguageServerException("Can't start ClangD language server", e);
        }
    }

    @Override
    protected LanguageServer connectToLanguageServer(Process languageServerProcess, LanguageClient client) throws LanguageServerException {
        Launcher<LanguageServer> launcher = Launcher.createLauncher(client, LanguageServer.class, languageServerProcess.getInputStream(),
                                                                    languageServerProcess.getOutputStream());
        launcher.startListening();
        return launcher.getRemoteProxy();
    }

    @Override
    public boolean isAbleToLaunch() {
        return true;//todo
    }


    static {
        description = new LanguageDescription();
        description.setFileExtensions(asList(EXTENSIONS));
        description.setLanguageId(LANGUAGE_ID);
        description.setMimeTypes(Arrays.asList(MIME_TYPES));
    }
}
