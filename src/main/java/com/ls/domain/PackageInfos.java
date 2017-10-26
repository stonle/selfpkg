package com.ls.domain;

import java.util.List;

public class PackageInfos {
   private List<PackageInfo> packageInfos;

public List<PackageInfo> getPackageInfos() {
	return packageInfos;
}

public void setPackageInfos(List<PackageInfo> packageInfos) {
	this.packageInfos = packageInfos;
}

@Override
public String toString() {
	return "PackageInfos [packageInfos=" + packageInfos + "]";
}
   
}
