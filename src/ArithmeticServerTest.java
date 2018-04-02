
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import java.util.Scanner;
import java.io.PrintWriter;
import org.junit.AfterClass;
import java.net.Socket;
import org.junit.BeforeClass;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import java.util.NoSuchElementException;

/**
 * Test cases for the {@code ArithmeticServer} class.
 *
 * <p>CS18000 -- Spring 2018 -- External Communication -- Homework</p>
 *
 * @author Logan Kulinski
 * @version March 25, 2018
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class ArithmeticServerTest {
    /*
     * Constants used in testing.
     */
    private static final String HOST;
    private static final int PORT;

    private static final String INVALID_REQUEST_TEST_ZERO_REQUEST;
    private static final String INVALID_REQUEST_TEST_ZERO_EXPECTED_RESPONSE;

    private static final String INVALID_REQUEST_TEST_ONE_REQUEST;
    private static final String INVALID_REQUEST_TEST_ONE_EXPECTED_RESPONSE;

    private static final String INVALID_REQUEST_TEST_TWO_REQUEST;
    private static final String INVALID_REQUEST_TEST_TWO_EXPECTED_RESPONSE;

    private static final String INVALID_REQUEST_TEST_THREE_REQUEST;
    private static final String INVALID_REQUEST_TEST_THREE_EXPECTED_RESPONSE;

    private static final String ADD_TEST_ZERO_REQUEST;
    private static final String ADD_TEST_ZERO_EXPECTED_RESPONSE;

    private static final String ADD_TEST_ONE_REQUEST;
    private static final String ADD_TEST_ONE_EXPECTED_RESPONSE;

    private static final String ADD_TEST_TWO_REQUEST;
    private static final String ADD_TEST_TWO_EXPECTED_RESPONSE;

    private static final String SUBTRACT_TEST_ZERO_REQUEST;
    private static final String SUBTRACT_TEST_ZERO_EXPECTED_RESPONSE;

    private static final String SUBTRACT_TEST_ONE_REQUEST;
    private static final String SUBTRACT_TEST_ONE_EXPECTED_RESPONSE;

    private static final String SUBTRACT_TEST_TWO_REQUEST;
    private static final String SUBTRACT_TEST_TWO_EXPECTED_RESPONSE;

    private static final String MULTIPLY_TEST_ZERO_REQUEST;
    private static final String MULTIPLY_TEST_ZERO_EXPECTED_RESPONSE;

    private static final String MULTIPLY_TEST_ONE_REQUEST;
    private static final String MULTIPLY_TEST_ONE_EXPECTED_RESPONSE;

    private static final String MULTIPLY_TEST_TWO_REQUEST;
    private static final String MULTIPLY_TEST_TWO_EXPECTED_RESPONSE;

    private static final String DIVIDE_TEST_ZERO_REQUEST;
    private static final String DIVIDE_TEST_ZERO_EXPECTED_RESPONSE;

    private static final String DIVIDE_TEST_ONE_REQUEST;
    private static final String DIVIDE_TEST_ONE_EXPECTED_RESPONSE;

    private static final String DIVIDE_TEST_TWO_REQUEST;
    private static final String DIVIDE_TEST_TWO_EXPECTED_RESPONSE;

    /*
     * Fields used in testing.
     */
    private static Thread serverThread;
    private static Scanner inServer;
    private static PrintWriter outServer;

    static {
        HOST = "localhost";
        PORT = 50_000;

        INVALID_REQUEST_TEST_ZERO_REQUEST = "LoganIsAwesome";
        INVALID_REQUEST_TEST_ZERO_EXPECTED_RESPONSE = String.format("%s: requests must include an operation name, and two operands all separated by spaces", ArithmeticProtocol.ILLEGAL_REQUEST);

        INVALID_REQUEST_TEST_ONE_REQUEST = "Logan 1 2";
        INVALID_REQUEST_TEST_ONE_EXPECTED_RESPONSE = String.format("%s: the operation name must be part of the protocol", ArithmeticProtocol.ILLEGAL_REQUEST);

        INVALID_REQUEST_TEST_TWO_REQUEST = "ADD Hi 10";
        INVALID_REQUEST_TEST_TWO_EXPECTED_RESPONSE = String.format("%s: the first operand must be a valid integer", ArithmeticProtocol.ILLEGAL_REQUEST);

        INVALID_REQUEST_TEST_THREE_REQUEST = "ADD 14 Hey";
        INVALID_REQUEST_TEST_THREE_EXPECTED_RESPONSE = String.format("%s: the second operand must be a valid integer", ArithmeticProtocol.ILLEGAL_REQUEST);

        ADD_TEST_ZERO_REQUEST = "ADD 10 15";
        ADD_TEST_ZERO_EXPECTED_RESPONSE = "25";

        ADD_TEST_ONE_REQUEST = "ADD 0 32";
        ADD_TEST_ONE_EXPECTED_RESPONSE = "32";

        ADD_TEST_TWO_REQUEST = "ADD 0 3";
        ADD_TEST_TWO_EXPECTED_RESPONSE = "3";

        SUBTRACT_TEST_ZERO_REQUEST = "SUBTRACT 16 5";
        SUBTRACT_TEST_ZERO_EXPECTED_RESPONSE = "11";

        SUBTRACT_TEST_ONE_REQUEST = "SUBTRACT 10 0";
        SUBTRACT_TEST_ONE_EXPECTED_RESPONSE = "10";

        SUBTRACT_TEST_TWO_REQUEST = "SUBTRACT 0 1";
        SUBTRACT_TEST_TWO_EXPECTED_RESPONSE = "-1";

        MULTIPLY_TEST_ZERO_REQUEST = "MULTIPLY 10 10";
        MULTIPLY_TEST_ZERO_EXPECTED_RESPONSE = "100";

        MULTIPLY_TEST_ONE_REQUEST = "MULTIPLY 0 5";
        MULTIPLY_TEST_ONE_EXPECTED_RESPONSE = "0";

        MULTIPLY_TEST_TWO_REQUEST = "MULTIPLY 1000 0";
        MULTIPLY_TEST_TWO_EXPECTED_RESPONSE = "0";

        DIVIDE_TEST_ZERO_REQUEST = "DIVIDE 20 4";
        DIVIDE_TEST_ZERO_EXPECTED_RESPONSE = "5";

        DIVIDE_TEST_ONE_REQUEST = "DIVIDE 45 45";
        DIVIDE_TEST_ONE_EXPECTED_RESPONSE = "1";

        DIVIDE_TEST_TWO_REQUEST = "DIVIDE 0 67";
        DIVIDE_TEST_TWO_EXPECTED_RESPONSE = "0";
    } //static

    @BeforeClass
    public static void setUp() throws IOException {
        ArithmeticServer server = new ArithmeticServer(PORT);
        Socket serverConnection;
        Runnable serveClients = server::serveClients;
        serverThread = new Thread(serveClients);

        serverThread.start();

        serverConnection = new Socket(HOST, PORT);
        inServer = new Scanner(serverConnection.getInputStream());
        outServer = new PrintWriter(serverConnection.getOutputStream(), true);
    } //setUp

    @AfterClass
    public static void cleanUp() {
        serverThread.interrupt();
    } //cleanUp

    @Test(timeout = 1000)
    public void _00_invalidRequestTestZero() {
        String response;

        outServer.println(INVALID_REQUEST_TEST_ZERO_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and sends the specified error message if it is not composed of three parts!", INVALID_REQUEST_TEST_ZERO_EXPECTED_RESPONSE, response);
    } //_00_invalidRequestTestZero

    @Test(timeout = 1000)
    public void _01_invalidRequestTestOne() {
        String response;

        outServer.println(INVALID_REQUEST_TEST_ONE_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and sends the specified error message if the operation in the request is not part of the protocol!", INVALID_REQUEST_TEST_ONE_EXPECTED_RESPONSE, response);
    } //_01_invalidRequestTestOne

    @Test(timeout = 1000)
    public void _02_invalidRequestTestTwo() {
        String response;

        outServer.println(INVALID_REQUEST_TEST_TWO_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and sends the specified error message if the first integer in the request is not valid!", INVALID_REQUEST_TEST_TWO_EXPECTED_RESPONSE, response);
    } //_02_invalidRequestTestTwo

    @Test(timeout = 1000)
    public void _03_invalidRequestTestThree() {
        String response;

        outServer.println(INVALID_REQUEST_TEST_THREE_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and sends the specified error message if the second integer in the request is not valid!", INVALID_REQUEST_TEST_THREE_EXPECTED_RESPONSE, response);
    } //_03_invalidRequestTestThree

    @Test(timeout = 1000)
    public void _04_addTestZero() {
        String response;

        outServer.println(ADD_TEST_ZERO_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and returns the sum of the two specified operands!", ADD_TEST_ZERO_EXPECTED_RESPONSE, response);
    } //_04_addTestZero

    @Test(timeout = 1000)
    public void _05_addTestOne() {
        String response;

        outServer.println(ADD_TEST_ONE_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and returns the sum of the two specified operands!", ADD_TEST_ONE_EXPECTED_RESPONSE, response);
    } //_05_addTestOne

    @Test(timeout = 1000)
    public void _06_addTestTwo() {
        String response;

        outServer.println(ADD_TEST_TWO_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and returns the sum of the two specified operands!", ADD_TEST_TWO_EXPECTED_RESPONSE, response);
    } //_06_addTestTwo

    @Test(timeout = 1000)
    public void _07_subtractTestZero() {
        String response;

        outServer.println(SUBTRACT_TEST_ZERO_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and returns the difference between the two specified operands!", SUBTRACT_TEST_ZERO_EXPECTED_RESPONSE, response);
    } //_07_subtractTestZero

    @Test(timeout = 1000)
    public void _08_subtractTestOne() {
        String response;

        outServer.println(SUBTRACT_TEST_ONE_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and returns the difference between the two specified operands!", SUBTRACT_TEST_ONE_EXPECTED_RESPONSE, response);
    } //_08_subtractTestOne

    @Test(timeout = 1000)
    public void _09_subtractTestTwo() {
        String response;

        outServer.println(SUBTRACT_TEST_TWO_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and returns the difference between the two specified operands!", SUBTRACT_TEST_TWO_EXPECTED_RESPONSE, response);
    } //_09_subtractTestTwo

    @Test(timeout = 1000)
    public void _10_multiplyTestZero() {
        String response;

        outServer.println(MULTIPLY_TEST_ZERO_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and returns the product of the two specified operands!", MULTIPLY_TEST_ZERO_EXPECTED_RESPONSE, response);
    } //_10_multiplyTestZero

    @Test(timeout = 1000)
    public void _11_multiplyTestOne() {
        String response;

        outServer.println(MULTIPLY_TEST_ONE_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and returns the product of the two specified operands!", MULTIPLY_TEST_ONE_EXPECTED_RESPONSE, response);
    } //_11_multiplyTestOne

    @Test(timeout = 1000)
    public void _12_multiplyTestTwo() {
        String response;

        outServer.println(MULTIPLY_TEST_TWO_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and returns the product of the two specified operands!", MULTIPLY_TEST_TWO_EXPECTED_RESPONSE, response);
    } //_12_multiplyTestTwo

    @Test(timeout = 1000)
    public void _13_divideTestZero() {
        String response;

        outServer.println(DIVIDE_TEST_ZERO_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and returns the quotient of the two specified operands!", DIVIDE_TEST_ZERO_EXPECTED_RESPONSE, response);
    } //_13_divideTestZero

    @Test(timeout = 1000)
    public void _14_divideTestOne() {
        String response;

        outServer.println(DIVIDE_TEST_ONE_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and returns the quotient of the two specified operands!", DIVIDE_TEST_ONE_EXPECTED_RESPONSE, response);
    } //_14_divideTestOne

    @Test(timeout = 1000)
    public void _15_divideTestTwo() {
        String response;

        outServer.println(DIVIDE_TEST_TWO_REQUEST);

        if (inServer.hasNextLine()) {
            response = inServer.nextLine();
        } else {
            throw new NoSuchElementException();
        } //end if

        Assert.assertEquals("Ensure that your server parses the client's request, and returns the quotient of the two specified operands!", DIVIDE_TEST_TWO_EXPECTED_RESPONSE, response);
    } //_15_divideTestTwo
}