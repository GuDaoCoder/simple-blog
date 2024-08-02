package com.github.biz.support;

import com.github.common.util.UrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.TransportCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Git操作
 *
 * @author Gudao
 * @since 2024/8/2
 */
public final class GitOperation {

	/**
	 * git仓库地址
	 */
	private final String url;

	/**
	 * 本地仓库地址
	 */
	private final String localPath;

	/**
	 * git用户名
	 */
	private final String username;

	/**
	 * git用户密码
	 */
	private final String password;

	/**
	 * git对象
	 */
	private final Git git;

	/**
	 * 执行的操作
	 */
	private final List<Runnable> operates = new ArrayList<>();

	private GitOperation(Builder builder) throws GitAPIException {
		this.url = builder.url;
		this.localPath = builder.localPath;
		this.username = builder.username;
		this.password = builder.password;
		try (Git localGit = init()) {
			this.git = localGit;
			if (StringUtils.isNotBlank(builder.branch)) {
				checkout(builder.branch);
			}
		}
	}

	public GitOperation thenCheckout(String branch) {
		operates.add(() -> {
			try {
				checkout(branch);
			}
			catch (GitAPIException e) {
				throw new RuntimeException("failed to checkout branch: " + branch, e);
			}
		});
		return this;
	}

	public GitOperation thenPull() {
		operates.add(() -> {
			try {
				pull();
			}
			catch (GitAPIException e) {
				throw new RuntimeException("failed to pull changes", e);
			}
		});
		return this;
	}

	/**
	 * 真正执行
	 */
	public void execute() {
		operates.forEach(Runnable::run);
	}

	/**
	 * 切换到指定分支
	 * @param branch
	 * @return void
	 **/
	private void checkout(String branch) throws GitAPIException {
		if (localExistsBranch(branch)) {
			// 如果分支在本地已存在，直接checkout
			git.checkout().setCreateBranch(false).setName(branch).call();
		}
		else {
			// 如果分支在本地不存在，需要从远程分支上面checkout。
			git.checkout().setCreateBranch(true).setName(branch).setStartPoint("origin/" + branch).call();
		}
	}

	/**
	 * git pull
	 * @param
	 * @return void
	 **/
	private void pull() throws GitAPIException {
		PullCommand pullCommand = git.pull();
		appendCredentials(pullCommand);
		pullCommand.call();
	}

	/**
	 * 初始化Git
	 * @param
	 * @return Git
	 **/
	private Git init() throws GitAPIException {
		try {
			// 先尝试打开本地仓库
			return Git.open(new File(localPath + File.separator + ".git"));
		}
		catch (IOException e) {
			// 本地仓库不存在再去克隆
			return cloneProject();
		}
	}

	/**
	 * 本地是否存在分支
	 * @param branch
	 * @return boolean
	 **/
	private boolean localExistsBranch(String branch) throws GitAPIException {
		List<Ref> refs = git.branchList().call();
		for (Ref ref : refs) {
			if (ref.getName().contains(branch)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * clone仓库
	 * @param
	 * @return Git
	 **/
	private Git cloneProject() throws GitAPIException {
		CloneCommand cloneCommand = Git.cloneRepository().setURI(url).setDirectory(new File(localPath));
		appendCredentials(cloneCommand);
		return cloneCommand.call();
	}

	private void appendCredentials(TransportCommand<?, ?> command) {
		if (StringUtils.isNoneBlank(username, password)) {
			command.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
		}
	}

	public static class Builder {

		private String url;

		private String branch;

		private String localPath;

		private String username;

		private String password;

		public Builder url(String url) {
			if (url == null) {
				throw new IllegalArgumentException("url is null");
			}
			if (!UrlUtil.isValidUrl(url)) {
				throw new IllegalArgumentException("url is invalid");
			}
			this.url = url;
			return this;
		}

		public Builder branch(String branch) {
			if (StringUtils.isBlank(branch)) {
				throw new IllegalArgumentException("branch is null");
			}
			this.branch = branch;
			return this;
		}

		public Builder localPath(String localPath) {
			if (localPath == null) {
				throw new IllegalArgumentException("localPath is null");
			}
			this.localPath = localPath;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public GitOperation build() throws GitAPIException {
			return new GitOperation(this);
		}

	}

}
