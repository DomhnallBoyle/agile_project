using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Collections.Generic;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using CSC3045_CS2.Exception;
using OxyPlot;
using OxyPlot.Series;
using OxyPlot.Axes;

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for SprintDashboard.xaml
    /// </summary>
    public partial class SprintDashboard : BasePage, INotifyPropertyChanged
    {
        #region Private variables

        private SprintClient _sprintClient;
        private TaskClient _taskClient;
        private Boolean _fromFile;
        private List<Task> _tasks { get; set; }
        private PlotModel plotModel;
        private List<UserStory> _sprintBacklog { get; set; }
        #endregion

        #region Public variables

        public String SprintDetails { get; set; }
        public Sprint CurrentSprint { get; set; }
        public Task CurrentTask { get; set; }
        public List<UserStory> UserStories { get; set; }
        public Permissions Permissions { get; set; }
        public List<Task> Tasks
        {
            get { return _tasks; }
            set
            {
                _tasks = value;
                OnPropertyChanged();
            }
        }
        
        public PlotModel PlotModel
        {
            get { return plotModel; }
            set { plotModel = value; OnPropertyChanged("PlotModel"); }
        }

        #endregion

        public SprintDashboard(Sprint sprint, bool fromFile)
        {
            InitializeComponent();
            CurrentPage = this.Title;
            this._fromFile = fromFile;
            DataContext = this;

            _sprintClient = new SprintClient();
            _taskClient = new TaskClient();

            Permissions = new Permissions((User)Application.Current.Properties["user"], sprint.Project);
         
            sprint.Users = _sprintClient.GetSprintTeam(sprint.Project.Id, sprint.Id);
            UserStories = _sprintClient.GetSprintStories(sprint.Project.Id, sprint.Id);

            CurrentSprint = sprint;

            SprintDetails = string.Format("Name: {0}, Start Date: {1}, End Date: {2}", CurrentSprint.Name, CurrentSprint.StartDate, CurrentSprint.EndDate);

            _sprintBacklog = _sprintClient.GetSprintBacklog(sprint.Project.Id, sprint.Id);

            PlotGraph();
        }

        public void PlotGraph()
        {
            int sprintLength = (int)(CurrentSprint.EndDate - CurrentSprint.StartDate).TotalDays + 1;
            List<long> burndownHours = new List<long>();

            for (int i = 0; i < sprintLength; i++)
            {
                burndownHours.Add(0l);
            }

            foreach (UserStory userStory in _sprintBacklog)
            {
                userStory.Tasks = _taskClient.GetTasksByUserStoryId(userStory.Project.Id, userStory.Id);
                foreach (Task task in userStory.Tasks)
                {
                    List<TaskEstimate> dailyEstimateList = _taskClient.GetTaskEstimates(
                                                                        task.UserStory.Project.Id,
                                                                        task.UserStory.Id,
                                                                        task.Id);
                    for (int i = 0; i < dailyEstimateList.Count; i++)
                    {
                        burndownHours[i] += dailyEstimateList[i].Estimate;
                    }
                }
            }

            long maxHours = 0;
            for (int i = 0; i < burndownHours.Count; i++)
            {
                if (burndownHours[i] > maxHours)
                    maxHours = burndownHours[i];
            }

            PlotModel = new PlotModel { Title = "Burndown Chart" };

            LineSeries lineSeries = new LineSeries();
            PlotModel.Axes.Add(new DateTimeAxis { Position = AxisPosition.Bottom, Minimum = DateTimeAxis.ToDouble(CurrentSprint.StartDate), Maximum = DateTimeAxis.ToDouble(CurrentSprint.EndDate), StringFormat = "d/M", Title = "Date" });
            PlotModel.Axes.Add(new LinearAxis { Position = AxisPosition.Left, Minimum = 0, Maximum = (maxHours * 1.1) + 1, Title = "Hours" });
            for (int i = 0; i < sprintLength; i++)
            {
                lineSeries.Points.Add(new DataPoint(DateTimeAxis.ToDouble(CurrentSprint.StartDate.AddDays(i)), burndownHours[i]));
            }

            PlotModel.Series.Add(lineSeries);
        }

        #region Command and Event methods

        /// <summary>
        /// Double Click event for mouse
        /// Displays tasks from clicked user story
        /// </summary>
        public void ListBox_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            try
            {
                UserStory userStory = (UserStory)(sender as ListBoxItem).DataContext;
                List<Task> _tasks = _taskClient.GetTasksByUserStoryId(userStory.Project.Id, userStory.Id);
                Tasks = _tasks;
            }
            catch (RestResponseErrorException ex)
            {
                MessageBoxUtil.ShowErrorBox(ex.Message);
            }
        }

        /// <summary>
        /// Relay command for navigating to the edit sprint team page
        /// </summary>
        public ICommand NavigateToManageSprintsCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page editSprintTeam = new EditSprintTeam(CurrentSprint);

                    NavigationService.GetNavigationService(this).Navigate(editSprintTeam);
                });
            }
        }

        /// <summary>
        /// Relay command for navigating to the edit sprint backlog page
        /// </summary>
        public ICommand NavigateToManageSprintBacklogCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page editSprintBacklog = new EditSprintBacklog(CurrentSprint);

                    NavigationService.GetNavigationService(this).Navigate(editSprintBacklog);
                });
            }
        }

        /// <summary>
        /// Returns user to the previous page
        /// </summary>
        public ICommand BackCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page manageSprintsPage = new ManageSprints(CurrentSprint.Project);

                    NavigationService.GetNavigationService(this).Navigate(manageSprintsPage);
                });
            }
        }

        /// <summary>
        /// Assigns user to the task
        /// </summary>
        public ICommand AssignCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    CurrentTask = (Task)param;
                    CurrentTask.Assignee = ((User)Application.Current.Properties["user"]);
                    try
                    {
                        CurrentTask =_taskClient.UpdateTask(CurrentTask.UserStory.Project.Id, CurrentTask.UserStory.Id, CurrentTask);

                        MessageBoxUtil.ShowSuccessBox("Succesfully Updated");
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBoxUtil.ShowErrorBox(ex.Message);
                    }
                });
            }
        }

        /// <summary>
        /// Brings user to the manage tasks screen to add more tasks to user story
        /// </summary>
        public ICommand CreateTaskCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    UserStory userStory = (UserStory)param;
                    Page manageTaskPage = new CreateTask(userStory, CurrentSprint.Id);

                    NavigationService.GetNavigationService(this).Navigate(manageTaskPage);
                });
            }
        }

        private void StoryTasks_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            Task selectedTask = (Task)StoryTasks.SelectedItem;
            Page taskDetails = new TaskDetails(selectedTask);

            NavigationService.GetNavigationService(this).Navigate(taskDetails);
        }

        /// <summary>
        /// Method to alert UI of property changed
        /// </summary>
        /// <param name="propertyName"></param>
        private void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        public event PropertyChangedEventHandler PropertyChanged;

        #endregion

    }
}
