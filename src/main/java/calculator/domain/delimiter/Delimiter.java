package calculator.domain.delimiter;

public interface Delimiter {

    // 문자열이 처리가 가능한지를 판단하는 정책을 가지는 지를 판단함.
    boolean support(String expression);

    // 문자열 분리에 사용할 정규표현식
    public String getRegex(String expression);

    // 문자열에서 실제 숫자 컨텐츠
    public String getContent(String expression);
}
