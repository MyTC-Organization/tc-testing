package org.example.unit;

import jdk.jfr.Description;
import org.example.logic.LoggerImpl;
import org.example.logic.MyLogger;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.jupiter.api.Tag;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class TestLogger {
    private final static String TEST = "TEST";
    private final MyLogger myLogger;
    private final PrintStream myOutPrintStream;
    private final PrintStream myErrPrintStream;
    private final ByteArrayOutputStream myOutByteArrayOutputStream;
    private final ByteArrayOutputStream myErrByteArrayOutputStream;

    public TestLogger() {
        this.myLogger = new LoggerImpl();
        this.myOutByteArrayOutputStream = new ByteArrayOutputStream();
        this.myErrByteArrayOutputStream = new ByteArrayOutputStream();
        this.myOutPrintStream = System.out;
        this.myErrPrintStream = System.err;
    }

    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(this.myOutByteArrayOutputStream));
        System.setErr(new PrintStream(this.myErrByteArrayOutputStream));
    }

    @Test
    @Description("The info message should be printend on the standard output console.")
    @Tag("unit")
    public void testInfoOnStandardOutput() {
        this.myLogger.info(TEST);
        assertTrue(this.myOutByteArrayOutputStream.toString().contains(TEST));
    }

    @Test
    @Description("The error message should be printed on the standard error console.")
    @Tag("unit")
    public void testErrorOnStandardOutput() {
        this.myLogger.error(TEST);
        assertTrue(this.myErrByteArrayOutputStream.toString().contains(TEST));
	assertTrue(false);
    }

    @After
    public void restoreStreams() {
        System.setOut(this.myOutPrintStream);
        System.setErr(this.myErrPrintStream);
    }


}
