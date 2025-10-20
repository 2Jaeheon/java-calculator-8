package calculator.domain.delimiter;

public interface Delimiter {

    // 문자열이 처리가 가능한지를 판단하는 정책을 가지는 지를 판단함.
    boolean support(String expression);

    // 문자열을 토큰화해서 반환한다.
    String[] tokenize(String expression);
}
