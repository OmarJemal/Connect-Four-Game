


/**
 * This ConnectFourEnum class implements an enum class that can be used to make a game of connectFour.
 * @author Omar Jemal
 * @version 1.0
 * @since 01 - 10 - 2018
 */

public enum ConnectFourEnum {
    IN_PROGRESS("In progress") , RED ("Red"), BLACK ("Black"), DRAW ("It's a draw!"), EMPTY("empty");
    
    private String value;
    
    ConnectFourEnum( String value) {
        this.value = value;
    }
}