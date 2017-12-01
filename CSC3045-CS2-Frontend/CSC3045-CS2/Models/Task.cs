using RestSharp.Deserializers;
using System.ComponentModel;
using System.Runtime.CompilerServices;

namespace CSC3045_CS2.Models
{
    public class Task : INotifyPropertyChanged
    {
        [DeserializeAs(Name = "id")]
        public long Id { get; set; }

        [DeserializeAs(Name = "name")]
        public string Name { get; set; }

        [DeserializeAs(Name = "description")]
        public string Description { get; set; }

        [DeserializeAs(Name = "initialEstimate")]
        public int InitialEstimate { get; set; }

        [DeserializeAs(Name = "userStory")]
        public UserStory UserStory { get; set; }

        private User _assignee;

        [DeserializeAs(Name = "assignee")]
        public User Assignee
        {
            get { return _assignee; }
            set
            {
                _assignee = value;
                OnPropertyChanged();
            }
        }

        public Task() { }

        public Task(string name, string description, int initialEstimate, UserStory userStory, User assignee)
        {
            this.Name = name;
            this.Description = description;
            this.InitialEstimate = initialEstimate;
            this.UserStory = userStory;
            this.Assignee = assignee;
        }

        private void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        public event PropertyChangedEventHandler PropertyChanged;
    }
}
