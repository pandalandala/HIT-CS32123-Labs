package pattern;

public interface SelectionStrategy<C> {
	/**
	 * 遴选得到最后结果
	 */
	public C select();
}
