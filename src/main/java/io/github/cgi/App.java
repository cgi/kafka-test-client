package io.github.cgi;

import org.apache.commons.cli.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        CommandLine commandLine = OptionsConfig.parseOptions(args);
        if (commandLine == null) {
            System.exit(0);
        } else if ( commandLine.hasOption(OptionsConfig.SEND_OPT) ) {
            DataGenerator generator = new DataGenerator(
                    commandLine.getOptionValue(OptionsConfig.BOOTSTRAP_OPT),
                    commandLine.getOptionValue(OptionsConfig.TOPIC_OPT),
                    10
            );
            generator.sendData();
        } else if ( commandLine.hasOption( OptionsConfig.GET_OPT )) {
            DataConsumer consumer = new DataConsumer(
                    commandLine.getOptionValue(OptionsConfig.BOOTSTRAP_OPT),
                    commandLine.getOptionValue(OptionsConfig.GROUP_OPT),
                    commandLine.getOptionValue(OptionsConfig.TOPIC_OPT),
                    10
            );
            consumer.getMessages();
    } else {
            System.exit(1);
        }

    }
}
