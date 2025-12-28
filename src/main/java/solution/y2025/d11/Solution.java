package solution.y2025.d11;

import solution.ISolution;

import java.util.*;

public class Solution implements ISolution {

    private Map<Device, Set<Device>> deviceAdjacencyMap = null;
    private static final Device out = Device.of("out");

    @Override
    public Optional<String> solveLevelOne(List<String> input) {
        Map<Device, Set<Device>> adjacencyMap = getDeviceAdjacencyMap(input);
        Device you = Device.of("you");
        long paths = countAllPaths(you, out, adjacencyMap);
        return Optional.of(String.valueOf(paths));
    }

    @Override
    public Optional<String> solveLevelTwo(List<String> input) {
        Map<Device, Set<Device>> adjacencyMap = getDeviceAdjacencyMap(input);
        Device svr = Device.of("svr");
        Device fft = Device.of("fft");
        Device dac = Device.of("dac");
        long svrToFft = countAllPaths(svr, fft, adjacencyMap);
        System.out.println("svr -> fft: " + svrToFft);
        long svrToDac = countAllPaths(svr, dac, adjacencyMap);
        System.out.println("svr -> dac: " + svrToDac);
        long fftToDac = countAllPaths(fft, dac, adjacencyMap);
        System.out.println("fft -> dac: " + fftToDac);
        long dacToFft = countAllPaths(dac, fft, adjacencyMap);
        System.out.println("dac -> fft: " + dacToFft);
        long fftToOut = countAllPaths(fft, out, adjacencyMap);
        System.out.println("fft -> out: " + fftToOut);
        long dacToOut = countAllPaths(dac, out, adjacencyMap);
        System.out.println("dac -> out: " + dacToOut);

        // analysis of the input shows that there are no paths from dac -> fft
        // so, in order to count all paths from svr -> out that go through both dac and fft
        // we can simply take number of paths from svr -> fft, fft -> dac, dac -> out and multiply them
        return Optional.of(String.valueOf(svrToFft * fftToDac * dacToOut));
    }

    private Map<Device, Set<Device>> getDeviceAdjacencyMap(List<String> input) {
        if (deviceAdjacencyMap != null) {
            return deviceAdjacencyMap;
        }

        deviceAdjacencyMap = new HashMap<>();
        deviceAdjacencyMap.put(out, new HashSet<>());
        for (String line : input) {
            String[] split = line.split(":");
            Device device = new Device(split[0]);
            Set<Device> adjacents = deviceAdjacencyMap.getOrDefault(device, new HashSet<>());
            Arrays.stream(split[1].trim().split(" "))
                    .map(Device::new)
                    .forEach(adjacents::add);
            deviceAdjacencyMap.put(device, adjacents);
        }
        return deviceAdjacencyMap;
    }

    private long countAllPaths(
            Device start,
            Device end,
            Map<Device, Set<Device>> adjacencyMap
    ) {
        long result = 0;
        Map<Device, Long> pathCounters = new HashMap<>();
        for (Device device : adjacencyMap.get(start)) {
            result += countPathRecursively(device, end, adjacencyMap, pathCounters);
        }
        return result;
    }

    private long countPathRecursively(
            Device currentDevice,
            Device end,
            Map<Device, Set<Device>> adjacencyMap,
            Map<Device, Long> pathCounters
    ) {
        if (pathCounters.containsKey(currentDevice)) {
            return pathCounters.get(currentDevice);
        }
        if (currentDevice.equals(end)) {
            return 1;
        }
        long pathCount = 0;
        for (Device nextDevice : adjacencyMap.get(currentDevice)) {
            pathCount += countPathRecursively(nextDevice, end, adjacencyMap, pathCounters);
        }
        pathCounters.put(currentDevice, pathCount);
        return pathCount;
    }
}