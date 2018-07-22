% make this workspace shared
if matlab.engine.isEngineShared == false
    matlab.engine.shareEngine
    matlab.engine.engineName
else
    isThisShared = matlab.engine.isEngineShared
end


figure
boxplot(values', 'labels', cellstr(names))
title('title')

ax = gca; % current axis
ax.YLim = [0 ax.YLim(2)];

% Overlay the mean as green diamonds
hold on
plot(learned, 'dg')
hold off