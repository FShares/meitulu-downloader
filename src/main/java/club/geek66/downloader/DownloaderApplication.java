package club.geek66.downloader;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

@SpringBootApplication
/*@EnableJpaRepositories*/
@EnableConfigurationProperties
public class DownloaderApplication {

	/*public static void main(String[] args) {
	 *//*SpringApplication.run(DownloaderApplication.class, args);*//*
		SpringApplication application = new SpringApplication(DownloaderApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.setLogStartupInfo(false);
		application.run(args);
	}*/

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {

			JFrame frame = new JFrame("Main");
			frame.setSize(250, 250);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


			JProgressBar progressBar = new JProgressBar(0, 100);
			progressBar.setValue(0);
			progressBar.setStringPainted(true);
			JPanel panel = new JPanel();
			panel.add(progressBar);


			frame.setContentPane(panel);
			frame.setVisible(true);

			new Thread(() -> {
				for (int i = 0; i < 100; i++) {
					progressBar.setValue(i);

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).run();
		});
	}

	/*public static void main(String[] args) {
		JFrame jf = new JFrame("测试窗口");
		jf.setSize(250, 250);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		// 创建一个进度条
		final JProgressBar progressBar = new JProgressBar();

		// 设置进度的 最小值 和 最大值
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);

		// 设置当前进度值
		progressBar.setValue(0);

		// 绘制百分比文本（进度条中间显示的百分数）
		progressBar.setStringPainted(true);

		// 添加进度改变通知
		*//*progressBar.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println("当前进度值: " + progressBar.getValue() + "; " +
						"进度百分比: " + progressBar.getPercentComplete());
			}
		});*//*

		// 添加到内容面板
		panel.add(progressBar);

		jf.setContentPane(panel);
		jf.setVisible(true);

		// 模拟延时操作进度, 每隔 0.5 秒更新进度
		new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				progressBar.setValue(progressBar.getValue() + 1);
			}
		}).start();
	}*/

	public static class JNumberField extends JTextField {

		@Override
		protected Document createDefaultModel() {
			return new PlainDocument() {
				@Override
				public void insertString(int offs, String str, AttributeSet attributeSet) throws BadLocationException {
					if (StringUtils.isNumeric(str)) {
						super.insertString(offs, str, attributeSet);
					}
				}
			};
		}

	}

}

