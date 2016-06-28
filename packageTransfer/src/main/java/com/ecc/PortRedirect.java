package com.ecc;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 网络
 * @author liuxin
 *
 */
public class PortRedirect extends Thread {
	private int from;
	private InetSocketAddress target;
	private boolean dolog;
	private Map<Integer, FileChannel> logs;
	private Map<SocketChannel, SocketChannel> map;
	private boolean doStop;

	public PortRedirect(int from, InetSocketAddress target) throws IOException {
		this(from, target, false);
	}

	/**
	 * 
	 * @param from			本地监听端口
	 * @param target		转发地址IP  转发地址端口
	 * @param dolog
	 * @throws IOException
	 */
	public PortRedirect(int from, InetSocketAddress target, boolean dolog) throws IOException {
		this.logs = new HashMap<Integer, FileChannel>();
		this.map = new HashMap<SocketChannel, SocketChannel>();
		this.doStop = false;
		this.from = from;
		this.target = target;
		this.dolog = dolog;
	}

	private void close(SocketChannel one) throws IOException {
		if (one != null) {
			SocketChannel two = (SocketChannel) this.map.get(one);
			this.map.remove(one);
			one.close();
			closeLog(one);
			if (two != null) {
				this.map.remove(two);
				two.close();
				closeLog(two);
			}
		}
	}

	private void closeLog(SocketChannel one) throws IOException {
		if (this.dolog) {
			int code = one.hashCode();
			FileChannel file = (FileChannel) this.logs.get(Integer.valueOf(code));
			if (file != null) {
				file.close();
				this.logs.remove(Integer.valueOf(code));
			}
		}
	}

	private void createLog(SocketChannel one) throws FileNotFoundException {
		if (this.dolog) {
			int code = one.hashCode();

			@SuppressWarnings("resource")
			FileChannel channel = new FileOutputStream(String.valueOf(code) + ".log").getChannel();

			this.logs.put(Integer.valueOf(code), channel);
		}
	}

	private void writeBuf(ByteBuffer buf, ByteChannel two, int numBytesRead) throws IOException {
		buf.flip();
		int have = 0;
		while (have < numBytesRead)
			have += two.write(buf);
	}

	public void run() {
		Iterator<SocketChannel> localIterator1;
		System.out.println("开始转发---->");
		ServerSocketChannel ssChannel = null;
		Selector selector = null;

		ByteBuffer buf = ByteBuffer.allocateDirect(1024);
		try {
			selector = Selector.open();

			ssChannel = ServerSocketChannel.open();
			ssChannel.configureBlocking(false);
			ssChannel.socket().bind(new InetSocketAddress(this.from));

			while (!(this.doStop)) {
				SocketChannel one = ssChannel.accept();
				if (one != null) {
					try {
						one.configureBlocking(false);
						SocketChannel two = SocketChannel.open();
						two.configureBlocking(false);
						this.map.put(one, two);
						this.map.put(two, one);

						two.connect(this.target);
						two.finishConnect();

						one.register(selector, one.validOps());
						two.register(selector, two.validOps());

						if (!(this.dolog))
							break;
						createLog(one);
						createLog(two);
					} catch (IOException e) {
						e.printStackTrace();
						close(one);
					}
				} else if (selector.select(50L) > 0) {
					Iterator<?> it = selector.selectedKeys().iterator();
					boolean hasWrite = false;
					while (it.hasNext()) {
						SelectionKey selKey = (SelectionKey) it.next();

						it.remove();

						if ((selKey.isValid()) && (selKey.isReadable()))
							try {
								one = (SocketChannel) selKey.channel();
								SocketChannel two = (SocketChannel) this.map.get(one);
								if ((!(one.finishConnect())) || (!(two.finishConnect())) || (!(one.isConnected())) || (!(two.isConnected())))
									break;
								hasWrite = true;
								buf.clear();
								int numBytesRead = one.read(buf);
								if (numBytesRead == -1) {
									close(one);
									selKey.cancel();
									break;
								}
								if (this.dolog)
									writeBuf(buf, (ByteChannel) this.logs.get(Integer.valueOf(one.hashCode())), numBytesRead);

								writeBuf(buf, two, numBytesRead);
							} catch (IOException e) {
								e.printStackTrace();
								close(one);
								selKey.cancel();
							}
					}

					if (!(hasWrite))
						Thread.sleep(10L);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			if (selector != null) {
				try {
					selector.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			try {
				for (localIterator1 = new ArrayList<SocketChannel>((Collection<SocketChannel>) this.map.keySet()).iterator(); localIterator1.hasNext();) {
					SocketChannel one = (SocketChannel) localIterator1.next();
					close(one);
				}
			} catch (IOException one2) {
				one2.printStackTrace();
			}
			if (ssChannel == null)
				return;
			try {
				ssChannel.close();
			} catch (IOException one) {
				one.printStackTrace();
			}
		} finally {
			if (selector != null)
				try {
					selector.close();
				} catch (IOException one) {
					one.printStackTrace();
				}
			try {
				for (localIterator1 = new ArrayList<SocketChannel>((Collection<SocketChannel>) this.map.keySet()).iterator(); localIterator1.hasNext();) {
					SocketChannel one = (SocketChannel) localIterator1.next();
					close(one);
				}
			} catch (IOException one1) {
				one1.printStackTrace();
			}
			if (ssChannel != null) {
				try {
					ssChannel.close();
				} catch (IOException one) {
					one.printStackTrace();
				}
			}
		}
	}

	public void close() {
		this.doStop = true;
	}

	public static void main(String[] args) throws Exception {
		PortRedirect redirect = new PortRedirect(8000, new InetSocketAddress("81.33.13.6", 6023), true);
		redirect.start();
		System.out.println("ok....");
		if (System.in.read() > 0)
			redirect.close();
	}
}