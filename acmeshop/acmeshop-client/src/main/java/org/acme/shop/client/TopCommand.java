package org.acme.shop.client;

import picocli.CommandLine;

@io.quarkus.picocli.runtime.annotations.TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true, subcommands = {GetCommand.class, SubscribeCommand.class})
public class TopCommand {
}
