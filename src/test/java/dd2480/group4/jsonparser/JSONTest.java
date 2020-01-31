package dd2480.group4.jsonparser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class JSONTest {

    @Test
    public void OwnerTest() {
        // GIVEN
        Owner owner = new Owner();
        owner.setId(123456);

        // WHEN
        Integer ourId = owner.getId();

        // THEN
        assertEquals(123456, ourId, "Testing if id is correct");

        // GIVEN
        owner.setId(987654321);

        // WHEN
        Integer ourNewId = owner.getId();

        // THEN
        assertNotEquals(12345, ourNewId, "Testing that id is incorrect ");
    }

    @Test
    public void pushTest() {
        // GIVEN
        Push push = new Push();
        push.setBefore("6113728f27ae82c7b1a177c8d03f9e96e0adf246");

        // WHEN
        String ourSHA = push.getBefore();

        // THEN
        assertEquals("6113728f27ae82c7b1a177c8d03f9e96e0adf246", ourSHA, "Testing if SHA is correct");

        // GIVEN
        push.setBefore("0000000000000000000000000000000000000000");

        // WHEN
        String ourNewSHA = push.getBefore();

        // THEN
        assertNotEquals("12345", ourNewSHA, "Testing that SHA is incorrect ");
    }

    @Test
    public void repositoryTest() {
        // GIVEN
        var repository = new Repository();
        repository.setId(123456789);

        // WHEN
        Integer ourId = repository.getId();

        // THEN
        assertEquals(123456789, ourId, "Testing that id is correct");

        // GIVEN
        repository.setId(987654321);

        // WHEN
        Integer ourNewId = repository.getId();

        // THEN
        assertNotEquals(123456789, ourNewId, "Testing that id is incorrect");
    }
}