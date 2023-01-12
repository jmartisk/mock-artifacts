package org.acme.shop.client;

import picocli.CommandLine;

@io.quarkus.picocli.runtime.annotations.TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true,
    subcommands = {DynamicClientCommand.class, TypesafeClientCommand.class})
public class TopCommand {
}
