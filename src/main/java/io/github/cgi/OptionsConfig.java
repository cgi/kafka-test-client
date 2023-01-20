package io.github.cgi;

import org.apache.commons.cli.*;

public class OptionsConfig {

    public static final Option HELP_OPT = Option.builder().option("h")
            .longOpt("help")
            .hasArg(false)
            .optionalArg(true)
            .desc("show this help")
            .build();
    public static final Option BOOTSTRAP_OPT = Option.builder().option("b")
            .longOpt("bootstrap-server")
            .hasArg(true)
            .optionalArg(false)
            .desc("kafka server to connect")
            .build();
    public static final Option TOPIC_OPT = Option.builder().option("t")
            .longOpt("topic")
            .hasArg(true)
            .optionalArg(false)
            .desc("topic to work with")
            .build();
    public static final Option GROUP_OPT = Option.builder().option("z")
            .longOpt("group")
            .hasArg(true)
            .optionalArg(true)
            .desc("group id")
            .build();
    public static final Option VERBOSE_OPT = Option.builder().option("v")
            .longOpt("verbose")
            .hasArg(false)
            .optionalArg(true)
            .desc("more logging")
            .build();
    public static final Option SEND_OPT = Option.builder().option("s")
            .longOpt("send")
            .hasArg(false)
            .optionalArg(true)
            .desc("send data to topic")
            .build();

    public static final Option GET_OPT = Option.builder().option("g")
            .longOpt("get")
            .hasArg(false)
            .optionalArg(true)
            .desc("get data from topic")
            .build();
    public static Options createOptions(){
        Options options = new Options();

        options.addOption(HELP_OPT);
        options.addOption(BOOTSTRAP_OPT);
        options.addOption(TOPIC_OPT);
        options.addOption(GROUP_OPT);
        options.addOption(VERBOSE_OPT);
        options.addOption(SEND_OPT);
        options.addOption(GET_OPT);

        return  options;
    }

    public static CommandLine parseOptions( String[] args){
        Options options = OptionsConfig.createOptions();
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            if ( line.hasOption("help") ){
                printHelp(options);
                return null;
            }
            return line;
        } catch (ParseException e) {
            printHelp(options);
        }
        return null;
    }

    public static void printHelp(Options options){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("kafka-test-client", options);
    }
}
