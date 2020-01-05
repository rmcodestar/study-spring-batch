# spring batch study


1. h2 console
```
http://localhost:8080/h2-console
```

2. trigger job
```
http://localhost:8080/job/{jobName}
```

3. sample job
* execute log
```
INFO o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=sampleJob]] launched with the following parameters: [{executeEpochMilli=1578239424417}]
INFO o.s.batch.core.job.SimpleStepHandler     : Executing step: [testStep]

INFO c.study.springbatch.job.SampleJobConfig  : [STEP-before]

INFO c.study.springbatch.job.SampleJobConfig  : [CHUNK-before]
INFO c.study.springbatch.job.SampleJobConfig  : [PROCESSOR] BatchRecord(id=1, status=READY, createdDateTime=2020-01-06T00:50:20.781, updatedDateTime=2020-01-06T00:50:20.781)
INFO c.study.springbatch.job.SampleJobConfig  : [PROCESSOR] BatchRecord(id=2, status=READY, createdDateTime=2020-01-06T00:50:20.796, updatedDateTime=2020-01-06T00:50:20.796)
INFO c.study.springbatch.job.SampleJobConfig  : [PROCESSOR] BatchRecord(id=3, status=READY, createdDateTime=2020-01-06T00:50:20.796, updatedDateTime=2020-01-06T00:50:20.796)
INFO c.study.springbatch.job.SampleJobConfig  : [CHUNK-after] ItemCount: 3

INFO c.study.springbatch.job.SampleJobConfig  : [CHUNK-before]
INFO c.study.springbatch.job.SampleJobConfig  : [PROCESSOR] BatchRecord(id=4, status=READY, createdDateTime=2020-01-06T00:50:20.797, updatedDateTime=2020-01-06T00:50:20.797)
INFO c.study.springbatch.job.SampleJobConfig  : [PROCESSOR] BatchRecord(id=5, status=READY, createdDateTime=2020-01-06T00:50:20.797, updatedDateTime=2020-01-06T00:50:20.797)
INFO c.study.springbatch.job.SampleJobConfig  : [PROCESSOR] BatchRecord(id=6, status=READY, createdDateTime=2020-01-06T00:50:20.797, updatedDateTime=2020-01-06T00:50:20.797)
INFO c.study.springbatch.job.SampleJobConfig  : [CHUNK-after-error] summary: StepExecution: id=1, version=2, name=testStep, status=STARTED, exitStatus=EXECUTING, readCount=6, filterCount=0, writeCount=3 readSkipCount=0, writeSkipCount=0, processSkipCount=0, commitCount=1, rollbackCount=1

INFO c.study.springbatch.job.SampleJobConfig  : [CHUNK-before]
INFO c.study.springbatch.job.SampleJobConfig  : [PROCESSOR] BatchRecord(id=4, status=DONE, createdDateTime=2020-01-06T00:50:20.797, updatedDateTime=2020-01-06T00:50:20.797)
INFO c.study.springbatch.job.SampleJobConfig  : [PROCESSOR] BatchRecord(id=5, status=DONE, createdDateTime=2020-01-06T00:50:20.797, updatedDateTime=2020-01-06T00:50:20.797)
INFO c.study.springbatch.job.SampleJobConfig  : [CHUNK-after] ItemCount: 6

INFO c.study.springbatch.job.SampleJobConfig  : [CHUNK-before]
INFO c.study.springbatch.job.SampleJobConfig  : [PROCESSOR] BatchRecord(id=7, status=READY, createdDateTime=2020-01-06T00:50:20.798, updatedDateTime=2020-01-06T00:50:20.798)
INFO c.study.springbatch.job.SampleJobConfig  : [PROCESSOR] BatchRecord(id=8, status=READY, createdDateTime=2020-01-06T00:50:20.798, updatedDateTime=2020-01-06T00:50:20.798)
INFO c.study.springbatch.job.SampleJobConfig  : [PROCESSOR] BatchRecord(id=9, status=READY, createdDateTime=2020-01-06T00:50:20.799, updatedDateTime=2020-01-06T00:50:20.799)
INFO c.study.springbatch.job.SampleJobConfig  : [CHUNK-after] ItemCount: 9

INFO c.study.springbatch.job.SampleJobConfig  : [CHUNK-before]
INFO c.study.springbatch.job.SampleJobConfig  : [PROCESSOR] BatchRecord(id=10, status=READY, createdDateTime=2020-01-06T00:50:20.799, updatedDateTime=2020-01-06T00:50:20.799)
INFO c.study.springbatch.job.SampleJobConfig  : [CHUNK-after] ItemCount: 10
INFO c.study.springbatch.job.SampleJobConfig  : [STEP-after] readCount : 10, writeCount : 9, skipCount : 1, commitCount : 4, start: Mon Jan 06 00:50:24 KST 2020, end : null

INFO o.s.batch.core.step.AbstractStep         : Step: [testStep] executed in 353ms

INFO o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=sampleJob]] completed with the following parameters: [{executeEpochMilli=1578239424417}] and the following status: [COMPLETED] in 414ms
```