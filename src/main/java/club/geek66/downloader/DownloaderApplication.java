package club.geek66.downloader;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.Color;

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
			JProgressBar progressBar = new JProgressBar(0, 100);
			progressBar.setValue(0);
			progressBar.setStringPainted(true);
			JPanel panel = new JPanel();
			panel.add(progressBar);

			JFrame frame = new JFrame("Main");
			frame.setSize(250, 250);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(panel);
			frame.setVisible(true);

			JTextField textField = new JTextField();
			frame.add(textField);
		});
	}

	public static class MainFrame extends JFrame {

		private JButton btnDownload;

		private JProgressBar prgDownload;

		private JPanel pnlDownload;

		public MainFrame() {
			SwingUtilities.invokeLater(this::init);
		}

		private void init() {
			SwingUtilities.invokeLater(() -> {
				this.setTitle("Main");
				this.setVisible(true);
				this.setSize(1024, 768);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			});

			SwingUtilities.invokeLater(() -> {
				this.btnDownload = new JButton();
				this.btnDownload.setText("点击下载");
				this.btnDownload.addActionListener(event -> {
					new Timer(500, e -> {
						this.prgDownload.setValue(this.prgDownload.getValue() + 1);
					}).start();
				});

				this.prgDownload = new JProgressBar(0, 100);
				this.prgDownload.setStringPainted(true);
				this.prgDownload.setVisible(true);
				this.prgDownload.setBackground(Color.GREEN);

				this.pnlDownload = new JPanel();
				this.pnlDownload.setSize(300, 300);
				this.pnlDownload.add(this.btnDownload);
				this.pnlDownload.add(this.prgDownload);

				this.add(pnlDownload);
			});

		}

		public static void main(String[] args) {
			MainFrame main = new MainFrame();
		}

	}

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

