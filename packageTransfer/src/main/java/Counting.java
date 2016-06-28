public class Counting {
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			sb.append(i);
		}

		// 统计能被三整除的数
		// 长度跟标记关系
		int tab = 0;

		boolean b = true;
		while (sb.length() != 1) {
			System.out.println("aaaaaaa");
			for (int i = sb.length(); i != 1; i--) {
				System.out.println("bbbbb");
				if (b) {
					// 下一次开始的位置
					tab = sb.length() % 3 + 1;
					if (((i + 1) % 3) == 0) {
						sb.delete(i, i);
					}
					b = false;
				} else if (sb.length() == 2) {
					// 剩下两个元素的时候
					if (tab == 1) {
						sb.delete(1, 1);
					} else {
						sb.delete(0, 0);
					}
				} else {
					// 第二次
					if (((i + tab) % 3) == 0) {
						sb.delete(i + tab, i + tab);
					}
				}
			}
		}
		System.out.println(sb);
	}
}