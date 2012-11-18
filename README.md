
                                                                             
        |o               |              |    |              |    o               
    ,---|.,---.,---.,---.|--- ,---.,---.|    |    ,---.,---.|--- .,---.,---.,---.
    |   |||    |---'|    |    |    |   ||    |    |---'|    |    ||   ||   |`---.
    `---'``    `---'`---'`---'`---'`---'`---'`---'`---'`---'`---'``---'`   '`---'
                                                                                 

##Intro

Providing off-heap implementations of some Java collections using DirectMemory.

At the moment, java.util.List is implemented, others soon to follow.

## Usage

To use directcollections, you'll need to define a DirectMemory CacheService first.

	CacheService<String, Object> cacheService = new DirectMemory<String, Object>()
			.setInitialCapacity(100).setNumberOfBuffers(10).setSize(10000).newCacheService();

And then, the List is created in the following line:

	List offHeapList = new DirectMemoryList(cacheService);

And your Maven dependencies will look like this:

		<dependency>
			<groupId>com.thisismartin.directcollections</groupId>
			<artifactId>directcollections</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
		<dependency>
			<groupId>org.apache.directmemory</groupId>
			<artifactId>directmemory-cache</artifactId>
			<version>0.2-SNAPSHOT</version>
		</dependency>

## ToDo

 - Improvements in the List implementation (see comments in source code).
 - Implementing the java.util.Queue interface.
 - Making it available on the OSS Nexus repository.